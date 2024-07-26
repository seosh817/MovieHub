plugins {
    alias(libs.plugins.moviehub.android.feature)
    alias(libs.plugins.moviehub.android.library.compose)
}

android {
    namespace = "com.seosh817.moviehub.feature.movies"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.appcompat)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}