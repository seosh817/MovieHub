package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidLibrary()
        }
    }
}
