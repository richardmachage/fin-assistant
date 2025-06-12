package com.transsion.financialassistant.settings.screens.change_pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.repository.pin.PinRepo
import com.transsion.financialassistant.data.repository.security.SecurityRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePinViewModel @Inject constructor(
    private val pinRepo: PinRepo,
    private val securityRepo: SecurityRepo,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var _state = MutableStateFlow(ChangePinScreenState())
    val state = _state.asStateFlow()


    fun onOldPinChange(pin: String) {
        if (pin.length < 5) {
            _state.update { it.copy(oldPin = pin) }
        }
    }

    fun onNewPinChange(pin: String) {
        if (pin.length < 5) {
            _state.update { it.copy(newPin = pin) }
        }
    }

    fun onConfirmPinChange(pin: String) {
        if (pin.length < 5) {
            _state.update { it.copy(confirmPin = pin) }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun resetToast() {
        _state.update { it.copy(toastMessage = null) }
    }


    fun isPinSet(): Boolean = pinRepo.isPinSet()

    fun onSavePin(onSuccess: () -> Unit) {
        viewModelScope.launch {
            toggleLoading(true)

            if (isPinSet()) {
                val isVerified = try {
                    verifyPin(state.value.oldPin)
                } catch (e: Exception) {
                    showToast("Invalid old PIN")
                    toggleLoading(false)
                    return@launch
                }

                if (!isVerified) {
                    showToast("Invalid old PIN")
                    toggleLoading(false)
                    return@launch
                }

                pinRepo.setPin(
                    state.value.newPin,
                    onSuccess = {
                        toggleLoading(false)
                        onNewPinChange("")
                        onOldPinChange("")
                        onSuccess()
                    },
                    onFailure = {
                        toggleLoading(false)
                        showToast("Failed to change PIN")
                    }
                )
            } else {
                //Pin is not set
                pinRepo.setPin(
                    state.value.newPin,
                    onSuccess = {
                        toggleLoading(false)
                        onSuccess()
                    },
                    onFailure = {
                        toggleLoading(false)
                        showToast("Failed to change PIN")
                    }
                )
            }
        }
    }

    private fun verifyPin(pin: String): Boolean {
        val correctPin = sharedPreferences.loadData(SharedPreferences.PIN_KEY)
            ?: throw Exception("No PIN found")

        val decryptedPin = securityRepo.decryptData(correctPin)
        val hashedInput = securityRepo.doHash(pin)

        return hashedInput.contentEquals(decryptedPin)
    }

}