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
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Android-Review"
include(":app")
include(":android_review01_tedmoon")
include(":android_review01_kshn379")
include(":android_review01_baek08102")
include(":android_review02_tedmoon")
include(":android_review02_baek08102")
include(":android_review03_baek08102")
include(":android_review03_tedmoon")
include(":android_review03_kshn379")
include(":android_review04_baek08102")
include(":android_review04_tedmoon")
include(":android_review05_tedmoon")
