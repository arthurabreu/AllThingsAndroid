import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

val Project.catalog: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.configureKotlinJvm() {
    // Android modules expose KotlinAndroidProjectExtension; JVM modules expose KotlinJvmProjectExtension
    (extensions.findByType(KotlinAndroidProjectExtension::class.java)
        ?: extensions.findByType(KotlinJvmProjectExtension::class.java))
        ?.compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
}

fun Project.configureTestJUnit5() {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
