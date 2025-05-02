package com.transsion.financialassistant.feedback.screens.feedback

import androidx.compose.ui.graphics.ImageBitmap

data class FeedbackScreenState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val attachment: ImageBitmap? = null,
    val toastMessage: String? = null
)
