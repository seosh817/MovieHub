package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureHiltAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        configureHiltAndroid()
    }
}
