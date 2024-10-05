/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.feature.viedo_player.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.viedo_player.VideoPlayerRoute

internal const val VIDEO_ID = "videoId"
const val VIDEO_PLAYER_NAVIGATION_ROUTE = "video_player_route/{$VIDEO_ID}"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/video_player/{$VIDEO_ID}"

fun NavController.navigateToVideoPlayer(videoId: String) {
    navigate("video_player_route/$videoId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.videoPlayerScreen() {
    composable(
        route = VIDEO_PLAYER_NAVIGATION_ROUTE,
        arguments = listOf(
            navArgument(VIDEO_ID) { type = NavType.StringType },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        VideoPlayerRoute()
    }
}
