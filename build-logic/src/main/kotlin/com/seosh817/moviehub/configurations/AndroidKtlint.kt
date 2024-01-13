package com.seosh817.moviehub.configurations

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

internal fun Project.configureKtlint() {
    apply<KtlintPlugin>()

    tasks.getByPath("preBuild").dependsOn("ktlintFormat")
    extensions.configure<KtlintExtension> {
        version.set(libs.findVersion("ktlint").get().toString())
        android.set(true)
        ignoreFailures.set(false)
        outputToConsole.set(true)
        debug.set(true)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
        }
    }
}
