package com.seosh817.moviehub.configurations

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAndroidApplication() {
    with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
    }

    extensions.configure<ApplicationExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = 34
    }
}
