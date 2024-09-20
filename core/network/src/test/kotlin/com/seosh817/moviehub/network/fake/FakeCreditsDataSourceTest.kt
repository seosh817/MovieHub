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
import com.seosh817.moviehub.core.network.fake.FakeCreditsDataSource
import com.seosh817.moviehub.core.network.model.credits.NetworkCast
import com.seosh817.moviehub.core.network.model.credits.NetworkCrew
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class FakeCreditsDataSourceTest {

    private lateinit var fakeCreditsDataSource: FakeCreditsDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        fakeCreditsDataSource = FakeCreditsDataSource(
            dispatcher = testDispatcher,
            json = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestFakeAssetManager,
        )
    }

    @Test
    fun `Test deserialization of Credits`() = runTest(testDispatcher) {
        val response = (fakeCreditsDataSource.fetchCredits(0, null) as ResultState.Success)

        assertEquals(550, response.data.id)
        assertEquals(76, response.data.networkCast.size)
        assertEquals(175, response.data.networkCrew.size)
    }

    @Test
    fun `Test deserialization of Credit casts`() = runTest(testDispatcher) {
        val casts = listOf(
            NetworkCast(
                adult = false,
                gender = 2,
                id = 819,
                knownForDepartment = "Acting",
                name = "Edward Norton",
                originalName = "Edward Norton",
                popularity = 26.99,
                profilePath = "/8nytsqL59SFJTVYVrN72k6qkGgJ.jpg",
                castId = 4,
                character = "The Narrator",
                creditId = "52fe4250c3a36847f80149f3",
                order = 0,
            ),
            NetworkCast(
                adult = false,
                gender = 2,
                id = 287,
                knownForDepartment = "Acting",
                name = "Brad Pitt",
                originalName = "Brad Pitt",
                popularity = 45.202,
                profilePath = "/huV2cdcolEUwJy37QvH914vup7d.jpg",
                castId = 5,
                character = "Tyler Durden",
                creditId = "52fe4250c3a36847f80149f7",
                order = 1,
            ),
            NetworkCast(
                adult = false,
                gender = 1,
                id = 1283,
                knownForDepartment = "Acting",
                name = "Helena Bonham Carter",
                originalName = "Helena Bonham Carter",
                popularity = 22.112,
                profilePath = "/DDeITcCpnBd0CkAIRPhggy9bt5.jpg",
                castId = 285,
                character = "Marla Singer",
                creditId = "631f0de8bd32090082733691",
                order = 2,
            ),
        )

        assertEquals(casts, (fakeCreditsDataSource.fetchCredits(1, null) as ResultState.Success).data.networkCast.take(3))
    }

    @Test
    fun `Test deserialization of Credit crews`() = runTest(testDispatcher) {
        val crews = listOf(
            NetworkCrew(
                adult = false,
                gender = 2,
                id = 376,
                knownForDepartment = "Production",
                name = "Arnon Milchan",
                originalName = "Arnon Milchan",
                popularity = 2.931,
                profilePath = "/b2hBExX4NnczNAnLuTBF4kmNhZm.jpg",
                creditId = "55731b8192514111610027d7",
                department = "Production",
                job = "Executive Producer",
            ),
            NetworkCrew(
                adult = false,
                gender = 2,
                id = 605,
                knownForDepartment = "Costume & Make-Up",
                name = "Michael Kaplan",
                originalName = "Michael Kaplan",
                popularity = 4.294,
                profilePath = "/bNarnI5K4XYIKaHsX6HAitllyQr.jpg",
                creditId = "5894c4eac3a3685ec6000218",
                department = "Costume & Make-Up",
                job = "Costume Design",
            ),
            NetworkCrew(
                adult = false,
                gender = 2,
                id = 1254,
                knownForDepartment = "Production",
                name = "Art Linson",
                originalName = "Art Linson",
                popularity = 2.503,
                profilePath = "/gZbaXFTQCSWCq6vzE817NXIA4zC.jpg",
                creditId = "52fe4250c3a36847f8014a11",
                department = "Production",
                job = "Producer",
            ),
        )

        assertEquals(crews, (fakeCreditsDataSource.fetchCredits(1, null) as ResultState.Success).data.networkCrew.take(3))
    }
}
