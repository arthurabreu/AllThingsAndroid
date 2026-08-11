plugins {
    alias(libs.plugins.kotlin.jvm)
    jacoco
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.register("jacocoVerify") {
    dependsOn("jacocoTestReport")
    doLast {
        val xml = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        val alt = layout.buildDirectory.file("reports/jacoco/jacocoTestReport/jacocoTestReport.xml").get().asFile
        val report = listOf(xml, alt).firstOrNull { it.exists() }
            ?: error("JaCoCo XML missing")
        val text = report.readText()
        val missed = Regex("""type="INSTRUCTION" missed="(\d+)" covered="(\d+)"""")
            .findAll(text)
            .map { it.groupValues[1].toInt() to it.groupValues[2].toInt() }
            .fold(0 to 0) { acc, n -> acc.first + n.first to acc.second + n.second }
        val total = missed.first + missed.second
        val ratio = if (total == 0) 1.0 else missed.second.toDouble() / total
        check(ratio >= 0.90) { "Domain coverage ${(ratio * 100).toInt()}% < 90%" }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.kotlinx.coroutines.test)
}
