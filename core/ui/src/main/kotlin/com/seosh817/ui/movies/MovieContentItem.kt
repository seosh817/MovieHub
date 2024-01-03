package com.seosh817.ui.movies

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.component.DynamicAsyncImage
import com.seosh817.moviehub.core.designsystem.component.LoadingAnimation
import com.seosh817.moviehub.core.designsystem.theme.Dimens
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.ui.ktx.formatBackdropImageUrl
import com.seosh817.ui.ktx.formatPosterImageUrl

@Composable
fun MovieContentItem(
    context: Context,
    movie: MovieOverview,
    modifier: Modifier = Modifier
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

            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.inversePrimary
                            .copy(alpha = 0.5f)
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                MovieDescription(
                    title = movie.title,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                )
            }
        }
    }
}

@Composable
fun MovieDescription(
    title: String?,
    releaseDate: String?,
    voteAverage: Double?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(Dimens.dp_4)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title ?: "",
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        )
        Spacer(modifier = Modifier.height(Dimens.dp_4))
        MovieDescriptionItem(icon = Icons.Default.DateRange, text = releaseDate)
        MovieDescriptionItem(icon = Icons.Default.Star, text = String.format("%.1f", voteAverage))
    }
}

@Composable
fun MovieDescriptionItem(
    icon: ImageVector,
    text: String?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.width(Dimens.dp_8))
        Text(
            text = text ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}
