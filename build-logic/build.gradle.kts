import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless) apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "moviehub.android.application"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "moviehub.android.application.compose"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationFlavors") {
            id = "moviehub.android.application.flavors"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidApplicationSpotless") {
            id = "moviehub.android.application.spotless"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidApplicationSpotlessConventionPlugin"
        }
        register("androidLibrary") {
            id = "moviehub.android.library"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "moviehub.android.library.compose"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidLibraryComposeConventionPlugin"
        }
        register("androidRoom") {
            id = "moviehub.android.room"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidRoomConventionPlugin"
        }
        register("androidHilt") {
            id = "moviehub.android.hilt"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidHiltConventionPlugin"
        }
        register("kotlinHilt") {
            id = "moviehub.kotlin.hilt"
            implementationClass = "com.seosh817.moviehub.plugins.KotlinHiltConventionPlugin"
        }
        register("kotlinJvm") {
            id = "moviehub.kotlin.jvm.library"
            implementationClass = "com.seosh817.moviehub.plugins.KotlinJvmLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "moviehub.android.feature"
            implementationClass = "com.seosh817.moviehub.plugins.AndroidFeatureConventionPlugin"
        }
    }
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)
    configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            indentWithTabs(2)
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }
}