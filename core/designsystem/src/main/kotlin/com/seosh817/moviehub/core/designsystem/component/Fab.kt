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
package com.seosh817.moviehub.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.seosh817.moviehub.core.designsystem.R

@Composable
fun LikeFab(
    onFabClick: (Boolean) -> Unit,
    checked: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val addContentDescription = stringResource(R.string.add)
    FloatingActionButton(
        onClick = { onFabClick(!checked) },
        shape = MaterialTheme.shapes.small,
        // Semantics in parent due to https://issuetracker.google.com/184825850
        modifier = modifier.semantics {
            contentDescription = addContentDescription
        },
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorite icon toggle",
            tint = if (checked) Color.Red else Color.White,
        )
    }
}
