pluginManagement {
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

    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("commonscreenslibs") {
            from(files("gradle/commonscreenslibs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

rootProject.name = "AllThingsAndroid"
include(":app")
include(":commonscreens")
include(":core:common")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":core:database")
include(":core:ui")
include(":core:navigation")
include(":feature:home")
include(":feature:lab")
include(":feature:persistence")
include(":feature:lists")
include(":feature:shop")
include(":feature:maps")
include(":feature:firebase")
include(":feature:chat")
include(":feature:voice")
include(":feature:feedback")
