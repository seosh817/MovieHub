package com.seosh817.moviehub.core.domain.repository

import android.service.autofill.UserData
import androidx.paging.PagingData
import com.seosh817.moviehub.core.model.UserMovie
import kotlinx.coroutines.flow.Flow

interface UserMovieRepository {

    fun observeUserMovies(language: String?): Flow<PagingData<UserMovie>>

//    fun observeAllBookmarked(): Flow<PagingData<UserMovie>>
}