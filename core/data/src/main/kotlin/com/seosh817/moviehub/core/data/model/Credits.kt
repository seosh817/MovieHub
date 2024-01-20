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
        crew = crewEntity.map { it.asExternalModel() },
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
        job = job
    )
}
