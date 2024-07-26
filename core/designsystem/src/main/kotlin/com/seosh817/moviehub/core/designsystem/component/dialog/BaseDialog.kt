package com.seosh817.moviehub.core.designsystem.component.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.seosh817.moviehub.core.designsystem.R

@Composable
fun BaseDialog(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        icon = icon,
        text = content,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.text_dialog_ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                )
            }
        },
    )
}

