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
package com.seosh817.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.seosh817.moviehub.core.designsystem.component.LikeToggleButton
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.ktx.formatPosterImageUrl

@Composable
fun SearchRowItem(
    modifier: Modifier = Modifier,
    userMovie: UserMovie,
    onClickBookmark: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            AsyncImage(
                model = userMovie.posterPath?.formatPosterImageUrl,
                contentDescription = userMovie.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = userMovie.title ?: "", style = MaterialTheme.typography.titleMedium)
            }
            LikeToggleButton(checked = userMovie.isBookmarked, onCheckedClick = onClickBookmark)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@ThemePreviews
@Composable
fun SearchGridItemPreview() {
    val movieItem = UserMovie(
        adult = false,
        backdropPath = "",
        genreIds = listOf(),
        id = 0,
        originalLanguage = "ko",
        originalTitle = "originalTitle",
        overview = "overview",
        popularity = 0.0,
        posterPath = "",
        releaseDate = "",
        title = "title",
        video = false,
        voteAverage = 0.0,
        voteCount = 0,
        isBookmarked = false,
    )

    MovieHubTheme {
        SearchRowItem(
            userMovie = movieItem,
            onClickBookmark = { },
        )
    }
}
