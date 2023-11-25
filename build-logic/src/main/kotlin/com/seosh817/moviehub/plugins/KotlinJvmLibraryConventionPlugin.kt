package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinJvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with (pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
        }
        configureKotlinJvm()
    }
}
