plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.hilt)
}

android {
    namespace = "com.seosh817.moviehub.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}