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
package com.seosh817.moviehub.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.seosh817.moviehub.core.designsystem.component.dialog.BaseDialog
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.ui.RadioButtonRow

@Composable
fun AppLanguageSettingsDialog(
    appLanguage: AppLanguage,
    onLanguageClick: (AppLanguage) -> Unit,
    onDismiss: () -> Unit,
) {
    BaseDialog(
        title = stringResource(id = R.string.language),
        icon = {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = null,
            )
        },
        content = {
            Column {
                AppLanguage
                    .entries
                    .forEach { language ->
                        RadioButtonRow(
                            text = language.name,
                            selected = appLanguage == language,
                            onClick = { onLanguageClick(language) },
                        )
                    }
            }
        },
        onDismiss = onDismiss,
    )
}
