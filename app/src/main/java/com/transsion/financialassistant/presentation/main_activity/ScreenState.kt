package com.transsion.financialassistant.presentation.main_activity

import kotlin.time.Duration

data class ScreenState(
    val timeTaken: Duration = Duration.ZERO,
    val isLoading: Boolean = false,
    val toastMessage: String? = null
)
