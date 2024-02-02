plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.hilt)
    alias(libs.plugins.moviehub.android.room)
}

android {
    defaultConfig {
        testInstrumentationRunner =
            "com.seosh817.moviehub.core.testing.MovieHubTestRunner"
    }
    namespace = "com.seosh817.moviehub.core.database"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)

    androidTestImplementation(projects.core.testing)
}