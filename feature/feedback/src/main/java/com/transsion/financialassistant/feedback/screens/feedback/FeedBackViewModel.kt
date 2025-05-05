package com.transsion.financialassistant.feedback.screens.feedback

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
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

    fun onAttachmentChange(attachment: ImageBitmap?, imageUri: Uri?) {
        _state.update { it.copy(attachment = attachment, imageUri = imageUri) }
    }

    fun onSubmitFeedback(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        imageUrl: String? = null
    ) {

        viewModelScope.launch {
            isLoading(true)
            feedBackRepo.sendFeedback(
                feedback = FeedBack(
                    title = state.value.title,
                    description = state.value.description,
                    attachment = imageUrl
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

    private fun initCloudinaryIfNeeded(context: Context) {
        try {
            MediaManager.get()
        } catch (e: Exception) {
            val config = mapOf(
                "cloud_name" to "derwpisbt",
                "api_key" to "786455387851524"
            )

            MediaManager.init(context.applicationContext, config)
        }
    }


    fun uploadImage(
        context: Context, imageUri: Uri,
        onSuccess: (String?) -> Unit,
        onError: (String) -> Unit
    ) {
        initCloudinaryIfNeeded(context)
        MediaManager.get().upload(imageUri)
            .unsigned("fa_unsigned_preset")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    isLoading(true)
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    //now submit feedback to firebase
                    val imageUrl = resultData?.get("secure_url")?.toString()
                    onSuccess(imageUrl)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    isLoading(false)
                    onError(error?.description.toString())

                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                }
            })
            .dispatch()
    }


}