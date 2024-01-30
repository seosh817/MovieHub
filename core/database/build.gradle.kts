plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.hilt)
    alias(libs.plugins.moviehub.android.room)
}

android {
    namespace = "com.seosh817.moviehub.core.database"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}