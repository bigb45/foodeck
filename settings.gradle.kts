pluginManagement {
    repositories {
        google()
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

rootProject.name = "FoodDelivery"
include(":app")
include(":feature:authentication")
include(":core")
include(":core:domain")
include(":core:data")
include(":feature:home")
include(":feature:authentication:facebook")
include(":feature:authentication:email")
