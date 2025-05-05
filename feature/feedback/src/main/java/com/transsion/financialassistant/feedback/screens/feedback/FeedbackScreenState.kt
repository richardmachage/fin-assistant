package com.transsion.financialassistant.feedback.screens.feedback

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class FeedbackScreenState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val attachment: ImageBitmap? = null,
    val toastMessage: String? = null,
    val imageUri: Uri? = null,
)
