package com.seosh817.moviehub.configurations

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

enum class MovieHubFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null, val versionNameSuffix: String? = null) {
    dev(FlavorDimension.contentType, applicationIdSuffix = ".dev", versionNameSuffix = "-dev"),
    stage(FlavorDimension.contentType, applicationIdSuffix = ".stage", versionNameSuffix = "-stage"),
    live(FlavorDimension.contentType)
}

internal fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: MovieHubFlavor) -> Unit = {}
) {

    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            MovieHubFlavor
                .values()
                .forEach {
                    create(it.name) {
                        dimension = it.dimension.name
                        flavorConfigurationBlock(this, it)
                        if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                            if (it.applicationIdSuffix != null) {
                                applicationIdSuffix = it.applicationIdSuffix
                            }
                            if (it.versionNameSuffix != null) {
                                versionNameSuffix = it.versionNameSuffix
                            }
                        }
                    }
                }
        }
    }
}
