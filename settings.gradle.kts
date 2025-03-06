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
    }
}

rootProject.name = "FinancialAssistant"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


include(":app")
include(":feature:onboarding")
include(":core:data")
include(":core:presentation")
include(":core:background")
include(":core:security")
