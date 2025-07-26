package com.apsone.run.presentation.active_run

import com.apsone.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object Saved : ActiveRunEvent
}