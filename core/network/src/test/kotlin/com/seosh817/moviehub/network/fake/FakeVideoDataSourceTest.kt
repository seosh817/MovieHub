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
package com.seosh817.moviehub.network.fake

import JvmUnitTestFakeAssetManager
import com.seosh817.common.result.ResultState
import com.seosh817.moviehub.core.network.fake.FakeVideosDataSource
import com.seosh817.moviehub.core.network.model.video.NetworkVideo
import com.seosh817.moviehub.core.network.model.video.NetworkVideoResponse
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeVideoDataSourceTest {

    private lateinit var fakeVideosDataSource: FakeVideosDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        fakeVideosDataSource = FakeVideosDataSource(
            dispatcher = testDispatcher,
            json = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun `Test deserialization of MovieVideosResponse`() = runTest(testDispatcher) {
        val response = (fakeVideosDataSource.fetchVideos(550) as ResultState.Success<NetworkVideoResponse>).data

        assertEquals(2, response.videos.size)
        assertEquals(550, response.id)
    }

    @Test
    fun `Test deserialization of MovieVideos results`() = runTest(testDispatcher) {
        val videos = listOf(
            NetworkVideo(
                id = "639d5326be6d88007f170f44",
                iso6391 = "en",
                iso31661 = "US",
                key = "O-b2VfmmbyA",
                name = "Fight Club (1999) Trailer - Starring Brad Pitt, Edward Norton, Helena Bonham Carter",
                site = "YouTube",
                size = 720,
                type = "Trailer",
                official = false,
                publishedAt = "2016-03-05T02:03:14.000Z",
            ),
            NetworkVideo(
                id = "5c9294240e0a267cd516835f",
                iso6391 = "en",
                iso31661 = "US",
                key = "BdJKm16Co6M",
                name = "#TBT Trailer",
                site = "YouTube",
                size = 1080,
                type = "Trailer",
                official = true,
                publishedAt = "2014-10-02T19:20:22.000Z",
            ),
        )
        assertEquals(
            videos,
            (fakeVideosDataSource.fetchVideos(550) as ResultState.Success<NetworkVideoResponse>).data.videos,
        )
    }
}
