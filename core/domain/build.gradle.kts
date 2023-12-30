@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.kotlin.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.seosh817.moviehub.core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.common)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.paging)
    implementation(libs.timber)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}