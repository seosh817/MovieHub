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
package com.seosh817.moviehub.feature.viedo_player

import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
internal fun VideoPlayerRoute(
    modifier: Modifier = Modifier,
    viewModel: VideoPlayerViewModel = hiltViewModel(),
) {
    val videoId by viewModel.videoUrlStateFlow.collectAsStateWithLifecycle()

    VideoPlayerScreen(
        modifier = modifier,
        videoId = videoId,
    )
}

@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    videoId: String,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background),
    ) {
        YoutubeVideoPlayer(videoId = videoId)
    }
}

@Composable
fun YoutubeVideoPlayer(
    videoId: String,
    isPlaying: (Boolean) -> Unit = {},
    isLoading: (Boolean) -> Unit = {},
    onVideoEnded: () -> Unit = {},
) {
    val mContext = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    var youtubePlayer: YouTubePlayer? = null
    val youTubePlayerView = YouTubePlayerView(mContext)

    youTubePlayerView.addFullscreenListener(
        object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: android.view.View, exitFullscreen: () -> Unit) {}
            override fun onExitFullscreen() {}
        },
    )

    val playerStateListener = object : AbstractYouTubePlayerListener() {
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            youtubePlayer = youTubePlayer
            youTubePlayer.loadVideo(videoId, 0f)
        }

        override fun onStateChange(
            youTubePlayer: YouTubePlayer,
            state: PlayerConstants.PlayerState,
        ) {
            super.onStateChange(youTubePlayer, state)
            when (state) {
                PlayerConstants.PlayerState.BUFFERING -> {
                    isLoading.invoke(true)
                    isPlaying.invoke(false)
                }

                PlayerConstants.PlayerState.PLAYING -> {
                    isLoading.invoke(false)
                    isPlaying.invoke(true)
                }

                PlayerConstants.PlayerState.ENDED -> {
                    isPlaying.invoke(false)
                    isLoading.invoke(false)
                    onVideoEnded.invoke()
                }

                else -> {}
            }
        }

        override fun onError(
            youTubePlayer: YouTubePlayer,
            error: PlayerConstants.PlayerError,
        ) {
            super.onError(youTubePlayer, error)
            Log.d("!!!", "yt error: $error")
        }
    }

    val playerBuilder = IFramePlayerOptions.Builder().apply {
        fullscreen(1)
        autoplay(1)
        rel(1)
    }

    AndroidView(
        factory = {
            youTubePlayerView.apply {
                enableAutomaticInitialization = false
                enableBackgroundPlayback(true)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                initialize(playerStateListener, playerBuilder.build())
            }
        },
    )
    DisposableEffect(
        key1 = Unit,
        effect = {
            onDispose {
                youTubePlayerView.removeYouTubePlayerListener(playerStateListener)
                youTubePlayerView.release()
                youtubePlayer = null
            }
        },
    )
    DisposableEffect(lifeCycleOwner) {
        val lifecycle = lifeCycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    youtubePlayer?.play()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    youtubePlayer?.pause()
                }

                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
