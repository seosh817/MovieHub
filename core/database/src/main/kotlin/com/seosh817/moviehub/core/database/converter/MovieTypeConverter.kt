package com.seosh817.moviehub.core.database.converter

import androidx.room.TypeConverter
import com.seosh817.moviehub.core.model.MovieType

class MovieTypeConverter {

    @TypeConverter
    fun fromMovieType(value: MovieType) = value.name

    @TypeConverter
    fun toMovieType(value: String) = MovieType.fromValue(value)
}
