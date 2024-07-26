package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    modifier: Modifier,
    voteAverage: Double
) {
    Row(modifier = modifier) {
        val max = 10
        val starCount = 5
        repeat(starCount) { starIndex ->
            val voteStarCount = voteAverage / (max / starCount)
            val asset = when {
                voteStarCount >= starIndex + 1 -> Icons.Filled.Star
                voteStarCount in starIndex.toDouble()..(starIndex + 1).toDouble() -> Icons.Filled.StarHalf
                else -> Icons.Filled.StarOutline
            }
            Icon(imageVector = asset, contentDescription = null, tint = MaterialTheme.colorScheme.inversePrimary)
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}