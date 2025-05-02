package com.transsion.financialassistant.feedback.screens.feedback

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import com.transsion.financialassistant.feedback.model.FeedBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedBackViewModel @Inject constructor(
    private val feedBackRepo: FeedBackRepo
) : ViewModel() {
    private var _state = MutableStateFlow(FeedbackScreenState())
    val state = _state.asStateFlow()

    fun onTittleChange(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun onDescriptionChange(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun onAttachmentChange(attachment: ImageBitmap?) {
        _state.update { it.copy(attachment = attachment) }
    }

    fun onSubmitFeedback(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        viewModelScope.launch {
            isLoading(true)
            feedBackRepo.sendFeedback(
                feedback = FeedBack(
                    title = state.value.title,
                    description = state.value.description
                ),
                onSuccess = {
                    clearFields()
                    isLoading(false)
                    showToast(message = "Feedback sent successfully")
                    onSuccess()
                },
                onError = {
                    isLoading(false)
                    showToast(message = "Error: $it")
                    onError(it)
                }
            )
        }

    }

    private fun isLoading(status: Boolean) {
        _state.update { it.copy(isLoading = status) }
    }

    private fun clearFields() {
        _state.update {
            it.copy(
                title = "",
                description = "",
                attachment = null
            )
        }
    }

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun clearToast() {
        _state.update { it.copy(toastMessage = null) }
    }


}