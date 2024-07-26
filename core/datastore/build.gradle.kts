plugins {
    alias(libs.plugins.moviehub.android.library)
    alias(libs.plugins.moviehub.android.hilt)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.seosh817.moviehub.core.datastore"
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.protobuf.kotlin.lite)

    testImplementation(projects.core.datastoreTest)
    testImplementation(projects.core.testing)
}
