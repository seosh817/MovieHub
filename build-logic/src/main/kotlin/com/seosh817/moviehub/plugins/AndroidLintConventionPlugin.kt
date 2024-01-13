package com.seosh817.moviehub.plugins

import com.seosh817.moviehub.configurations.configureKtlint
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureKtlint()
    }
}
