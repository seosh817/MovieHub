plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.hilt)
}

android {
    namespace = "com.seosh817.moviehub.core.datastore.test"
}

dependencies {
    api(projects.core.datastore)
    api(libs.androidx.dataStore.core)

    implementation(libs.protobuf.kotlin.lite)
    implementation(projects.core.common)
    implementation(projects.core.testing)
}