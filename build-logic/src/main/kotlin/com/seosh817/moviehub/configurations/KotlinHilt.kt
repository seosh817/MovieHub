package com.seosh817.moviehub.configurations

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltKotlin() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.kapt")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt.core").get())
        "kapt"(libs.findLibrary("hilt.compiler").get())
    }
}
