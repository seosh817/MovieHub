pluginManagement {
    includeBuild("build-logic")
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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "MovieHub"
include(":app")
include(":common")
include(":core")
include(":core:network")
include(":core:model")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":core:designsystem")
include(":feature:movies")
include(":core:ui")
include(":core:datastore")
include(":feature:movie_detail")
include(":feature:settings")
include(":feature:bookmarks")
include(":core:database")
include(":core:testing")
