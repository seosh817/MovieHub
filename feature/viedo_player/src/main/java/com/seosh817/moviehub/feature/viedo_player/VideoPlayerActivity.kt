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

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerActivity : ComponentActivity() {

    private lateinit var youTubePlayerView: YouTubePlayerView
    private var youtubePlayer: YouTubePlayer? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(
                    android.app.PictureInPictureParams.Builder().build(),
                )
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoId = intent.getStringExtra(VIDEO_ID) ?: ""
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setContent {
            VideoPlayerScreenContent(videoId)
        }
    }

    @Composable
    fun VideoPlayerScreenContent(videoId: String) {
        val lifeCycleOwner = LocalLifecycleOwner.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .navigationBarsPadding()
                .background(MaterialTheme.colorScheme.background),
        ) {
            AndroidView(
                factory = {
                    YouTubePlayerView(this@VideoPlayerActivity).apply {
                        enableAutomaticInitialization = false
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )

                        addFullscreenListener(
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
                                    PlayerConstants.PlayerState.BUFFERING -> {}
                                    PlayerConstants.PlayerState.PLAYING -> {}
                                    PlayerConstants.PlayerState.ENDED -> {}
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

                        initialize(playerStateListener, playerBuilder.build())
                    }
                        .also {
                            youTubePlayerView = it
                        }
                },
            )
        }

        DisposableEffect(
            key1 = Unit,
            effect = {
                onDispose {
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

                    Lifecycle.Event.ON_DESTROY -> {
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

    companion object {
        const val VIDEO_ID = "videoId"
    }
}
