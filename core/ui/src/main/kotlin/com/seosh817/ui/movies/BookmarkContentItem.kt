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
package com.seosh817.ui.movies

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.component.DynamicAsyncImage
import com.seosh817.moviehub.core.designsystem.component.LikeToggleButton
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.ktx.formatPosterImageUrl

@Composable
fun BookmarkContentItem(
    context: Context,
    movie: UserMovie,
    modifier: Modifier = Modifier,
    onLikeClick: (UserMovie, Boolean) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            DynamicAsyncImage(
                context = context,
                modifier = Modifier
                    .fillMaxSize(),
                imageUrl = movie.posterPath?.formatPosterImageUrl,
                contentDescription = movie.title ?: "",
                contentScale = ContentScale.Crop,
                alpha = 1f,
            )

            LikeToggleButton(
                modifier = Modifier
                    .padding(AppDimens.PaddingSmall)
                    .align(Alignment.TopEnd),
                checked = movie.isBookmarked,
                onCheckedClick = {
                    onLikeClick(movie, it)
                },
            )
        }
    }
}
