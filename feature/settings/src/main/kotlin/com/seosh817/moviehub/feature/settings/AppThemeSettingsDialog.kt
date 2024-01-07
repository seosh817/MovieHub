package com.seosh817.moviehub.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.seosh817.moviehub.core.designsystem.component.dialog.BaseDialog
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.ui.RadioButtonRow

@Composable
fun AppThemeSettingsDialog(
    appThemeMode: DarkThemeMode,
    onThemeClick: (DarkThemeMode) -> Unit,
    onDismiss: () -> Unit,
) {
    BaseDialog(
        title = stringResource(id = R.string.app_theme),
        icon = {
            Icon(
                imageVector = Icons.Default.BrightnessMedium,
                contentDescription = null,
            )
        },
        content = {
            Column {
                DarkThemeMode
                    .entries
                    .forEach { darkThemeMode ->
                        RadioButtonRow(
                            text = darkThemeMode.name,
                            selected = appThemeMode == darkThemeMode,
                            onClick = { onThemeClick(darkThemeMode) },
                        )
                    }
            }
        },
        onDismiss = onDismiss,
    )
}
