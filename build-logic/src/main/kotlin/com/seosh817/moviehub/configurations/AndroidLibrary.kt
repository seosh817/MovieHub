package com.seosh817.moviehub.configurations

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

internal fun Project.configureAndroidLibrary() {
    with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
    }

    extensions.configure<LibraryExtension> {

        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 34

        configureFlavors(this)
    }

    dependencies {
        add("testImplementation", kotlin("test"))
        add("androidTestImplementation", kotlin("test"))
    }
}
