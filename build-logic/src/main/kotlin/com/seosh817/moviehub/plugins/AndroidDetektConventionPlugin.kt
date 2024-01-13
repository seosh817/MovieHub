package com.seosh817.moviehub.plugins

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidDetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        apply<DetektPlugin>()

        configure<DetektExtension> {
            config.from(file("$rootDir/config/detekt/detekt.yml"))

            buildUponDefaultConfig = true
            autoCorrect = true
            parallel = true
            ignoreFailures = false

            // By default detekt does not check test source set and gradle specific files,
            // so hey have to be added manually
            source.setFrom(
                "$rootDir/build.gradle.kts",
                "$rootDir/settings.gradle.kts",
                "src/main/kotlin",
                "src/main/java",
            )
        }
    }
}
