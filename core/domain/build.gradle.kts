plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.kotlin.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.seosh817.moviehub.core.domain"
}

dependencies {
    implementation(projects.common)

    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.database)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.paging)
    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}