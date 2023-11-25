package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureHiltKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        configureHiltKotlin()
    }
}
