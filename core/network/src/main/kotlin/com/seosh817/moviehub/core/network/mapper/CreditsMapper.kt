package com.seosh817.moviehub.core.network.mapper

import com.seosh817.moviehub.core.model.Cast
import com.seosh817.moviehub.core.model.Credits
import com.seosh817.moviehub.core.model.Crew
import com.seosh817.moviehub.core.network.model.credits.CastEntity
import com.seosh817.moviehub.core.network.model.credits.CreditsEntity
import com.seosh817.moviehub.core.network.model.credits.CrewEntity

fun CreditsEntity.asExternalModel(): Credits {
    return Credits(
        id = id,
        cast = castEntity.map { it.asExternalModel() },
        crew = crewEntity.map { it.asExternalModel() },
    )
}

fun CastEntity.asExternalModel(): Cast {
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

fun CrewEntity.asExternalModel(): Crew {
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
