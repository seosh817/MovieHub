plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.library.compose)
    alias(libs.plugins.moviehub.android.hilt)
}

android {
    namespace = "com.seosh817.moviehub.core.testing"
}

dependencies {
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.model)
}
