package com.seosh817.moviehub.core.designsystem.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.seosh817.moviehub.core.designsystem.R

val RectangleShape = RoundedCornerShape(0)

@Composable
fun DynamicAsyncImage(
    context: Context,
    modifier: Modifier = Modifier,
    imageUrl: String?,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f,
    shape: Shape = RectangleShape,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val asyncImagePainter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Empty
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            LoadingAnimation(
                modifier = Modifier
                    .align(Alignment.Center),
            )
        }

        if (isError) {
            Text(
                text = stringResource(R.string.no_image),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape),
            painter = asyncImagePainter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alpha = alpha,
        )
    }
}
