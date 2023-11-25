package com.seosh817.moviehub.configurations

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("org.jetbrains.kotlin.kapt")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "kapt"(libs.findLibrary("hilt.android.compiler").get())
        "kaptAndroidTest"(libs.findLibrary("hilt.android.compiler").get())
        "kaptTest"(libs.findLibrary("hilt.android.compiler").get())
    }
}
