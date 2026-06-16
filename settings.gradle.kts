pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "AllThingsAndroid"

// Legacy modules (being migrated)
include(":app")
include(":commonscreens")

// Core modules
include(":core:common")
include(":core:model")
include(":core:designsystem")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:testing")

// Feature modules
include(":feature:home")
include(":feature:apishowcase")
include(":feature:calculator")
include(":feature:login")
include(":feature:settings")
include(":feature:profile")
include(":feature:download")
include(":feature:meditation")
include(":feature:solid")
include(":feature:designprinciple")
include(":feature:olympics")
include(":feature:feed")
include(":feature:maps")
include(":feature:auth")
include(":feature:clouddb")
include(":feature:notifications")
include(":feature:player")
