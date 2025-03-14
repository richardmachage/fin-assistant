package com.transsion.financialassistant.main_activity

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.background.getMpesaMessages
import com.transsion.financialassistant.background.models.MpesaMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private var _timeTaken = MutableStateFlow(Duration.ZERO)
    val timeTaken = _timeTaken.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _messages = MutableStateFlow(emptyList<MpesaMessage>())
    val messages = _messages.asStateFlow()

    fun getTheMessages(contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _loadingState.update { true } }
            _messages.update {
                getMpesaMessages(
                    contentResolver = contentResolver,
                    getExecutionTime = {
                        this.launch(Dispatchers.Main) {
                            _timeTaken.update { it }
                            _loadingState.update { false }
                        }
                    }
                )
            }
        }
    }
}