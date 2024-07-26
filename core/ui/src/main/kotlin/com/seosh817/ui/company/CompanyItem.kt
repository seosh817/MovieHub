package com.seosh817.ui.company

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.component.DynamicAsyncImage

@Composable
fun CompanyItem(
    context: Context,
    modifier: Modifier = Modifier,
    imageUrl: String?,
    name: String?,
    contentDescription: String,
) {
    Column(
        modifier
            .padding(4.dp)
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DynamicAsyncImage(
            context = context,
            modifier = Modifier
                .height(80.dp)
                .fillMaxSize(),
            imageUrl = imageUrl,
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            alpha = 1f,
            shape = CircleShape
        )
        Text(
            text = name.orEmpty(),
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp)
                .width(IntrinsicSize.Max),
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
        )
    }
}