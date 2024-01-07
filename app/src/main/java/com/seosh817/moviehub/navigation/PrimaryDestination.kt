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
        labelResId = R.string.movies
    ),
    BOOK_MARKS(
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.Favorite,
        contentDescription = "Bookmarks",
        labelResId = R.string.bookmarks
    ),
}