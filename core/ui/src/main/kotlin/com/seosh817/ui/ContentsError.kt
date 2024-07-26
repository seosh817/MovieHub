package com.seosh817.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.core.designsystem.theme.ThemePreviews
import com.seosh817.moviehub.core.ui.R

@Composable
fun ContentsError(
    cause: String,
    message: String? = null,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            message ?: stringResource(id = R.string.refresh_error),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
        Text(
            cause,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(AppDimens.PaddingSmall))
        Button(
            onClick = onRefresh,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            content = {
                Text(
                    text = "Refresh",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                    textAlign = TextAlign.Center
                )
            },
        )
    }
}

@Composable
@ThemePreviews
private fun ContentsErrorPreview() {
    MovieHubTheme {
        ContentsError(
            "Error",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary),
            onRefresh = {}
        )
    }
}