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
package com.seosh817.moviehub.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.seosh817.moviehub.R

enum class PrimaryDestination(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val contentDescription: String,
    @StringRes val labelResId: Int,
) {
    MOVIES(
        selectedIcon = Icons.Filled.Movie,
        unSelectedIcon = Icons.Outlined.Movie,
        contentDescription = "Movies",
        labelResId = R.string.movies,
    ),
    BOOKMARKS(
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.Favorite,
        contentDescription = "Bookmarks",
        labelResId = R.string.bookmarks,
    ),
}
