package com.seosh817.ui.movies

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.component.DynamicAsyncImage
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.ktx.formatPosterImageUrl

@Composable
fun BookmarkContentItem(
    context: Context,
    movie: UserMovie,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DynamicAsyncImage(
                context = context,
                modifier = Modifier
                    .fillMaxSize(),
                imageUrl = movie.posterPath?.formatPosterImageUrl,
                contentDescription = movie.title ?: "",
                contentScale = ContentScale.Crop,
                alpha = 1f
            )
        }
    }
}
