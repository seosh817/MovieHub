package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureAndroidApplication
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidApplication()
        }
    }
}
