plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        dependencies {
            add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher:1.13.3")
        }
    }
    pluginManager.withPlugin("com.android.library") {
        dependencies {
            add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher:1.13.3")
        }
    }
}
