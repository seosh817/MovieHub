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
package com.seosh817.moviehub.core.data.model

import com.seosh817.moviehub.core.model.Cast
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.Crew
import com.seosh817.moviehub.core.network.model.credits.NetworkCast
import com.seosh817.moviehub.core.network.model.credits.NetworkCredits
import com.seosh817.moviehub.core.network.model.credits.NetworkCrew

fun NetworkCredits.asExternalModel(): Credits {
    return Credits(
        id = id,
        cast = networkCast.map { it.asExternalModel() },
        crew = networkCrew.map { it.asExternalModel() },
    )
}

fun NetworkCast.asExternalModel(): Cast {
    return Cast(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath,
        castId = castId,
        character = character,
        creditId = creditId,
        order = order,
    )
}

fun NetworkCrew.asExternalModel(): Crew {
    return Crew(
        adult = adult,
        gender = gender,
        id = id,
        knownForDepartment = knownForDepartment,
        name = name,
        originalName = originalName,
        popularity = popularity,
        profilePath = profilePath,
        creditId = creditId,
        department = department,
        job = job,
    )
}
