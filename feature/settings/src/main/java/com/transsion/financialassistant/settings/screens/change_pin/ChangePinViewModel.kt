package com.transsion.financialassistant.settings.screens.change_pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePinViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableStateFlow(ChangePinScreenState())
    val state = _state.asStateFlow()


    fun onOldPinChange(pin: String) {
        _state.update { it.copy(oldPin = pin) }
    }

    fun onNewPinChange(pin: String) {
        _state.update { it.copy(newPin = pin) }
    }

    fun toggleLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun resetToast() {
        _state.update { it.copy(toastMessage = null) }
    }

    fun onSavePin(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            //TODO: save pin

            toggleLoading(true)
            delay(2)
            toggleLoading(false)
            onSuccess()
        }
    }
}