plugins {
    alias(libs.plugins.moviehub.android.feature)
    alias(libs.plugins.moviehub.android.library.compose)
}

android {
    namespace = "com.seosh817.moviehub.feature.movie_detail"
}

dependencies {
    implementation(projects.common)
    implementation(libs.androidx.activity.compose)
    implementation(libs.appcompat)
    implementation(libs.landscapist.coil)
    implementation(libs.landscapist.animation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}