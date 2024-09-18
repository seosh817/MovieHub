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
package com.seosh817.moviehub.core.model

data class Credits(
    val id: Long,
    val cast: List<Cast>?,
    val crew: List<Crew>?,
)

data class Cast(
    val adult: Boolean?,
    val gender: Long?,
    val id: Long,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?,
    val castId: Long?,
    val character: String?,
    val creditId: String?,
    val order: Long?,
)

data class Crew(
    val adult: Boolean?,
    val gender: Long?,
    val id: Long,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?,
    val creditId: String?,
    val department: String?,
    val job: String?,
)
