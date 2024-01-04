package com.seosh817.ui.scroll

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

private val HeaderTransitionOffset = 160.dp

data class TransitionScroller(
    val scrollState: ScrollState,
    val transitionPosition: Float
) {
    val toolbarTransitionState = MutableTransitionState(ToolbarState.HIDDEN)

    fun getToolbarState(density: Density): ToolbarState {
        return if (transitionPosition > 1f &&
            scrollState.value > (transitionPosition - getTransitionOffset(density))
        ) {
            toolbarTransitionState.targetState = ToolbarState.SHOWN
            ToolbarState.SHOWN
        } else {
            toolbarTransitionState.targetState = ToolbarState.HIDDEN
            ToolbarState.HIDDEN
        }
    }

    private fun getTransitionOffset(density: Density): Float = with(density) {
        HeaderTransitionOffset.toPx()
    }
}

enum class ToolbarState { HIDDEN, SHOWN }

val ToolbarState.isShown
    get() = this == ToolbarState.SHOWN
