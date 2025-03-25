package com.transsion.financialassistant.onboarding.screens.create_pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePinScreenViewModel @Inject constructor(
    private val repository: OnboardingRepo
): ViewModel() {
    //private val _pinState = MutableStateFlow<PinState>(PinState.Idle)
    private val _pinState = MutableStateFlow(CreatePinScreenState()) // Default state

    //val pinState : StateFlow<PinState> = _pinState
    val pinState: StateFlow<CreatePinScreenState> =
        _pinState.asStateFlow() // Expose the state as a read-only StateFlow


    private var _showPrompt = MutableStateFlow(true)
    val showPrompt = _showPrompt.asStateFlow()


    fun setShowPrompt(show: Boolean) {
        _showPrompt.update { show }
    }

    fun setUserPin(pin: String) {
        viewModelScope.launch {

            // ---- Loading state ------
            //_pinState.value = PinState.Loading
            _pinState.update {
                it.copy(isLoading = true, isIdle = false, error = null, toastMessage = null)
            }

            repository.setPin(
                pin = pin,
                onSuccess = {
                    // ----Success state ------
                    //_pinState.value = PinState.Success
                    // check if pin meets requirements
                    if (pin.length >= 4) {
                        _pinState.update {
                            it.copy(
                                success = true,
                                isLoading = false,
                                error = null,
                                //toastMessage = "Pin Created Successfully"
                            )
                        }
                    } else {
                        _pinState.update {
                            it.copy(
                                success = false,
                                isLoading = false,
                                error = "Pin must be at least 4 digits",
                                //toastMessage = "Pin must be at least 4 digits"
                            )
                        }
                    }
                },
                onFailure = {
                    // ----Error state ------
                    //_pinState.value = PinState.Error(message = it)
                    _pinState.value = CreatePinScreenState(error = it)
                }
            )
        }
    }

    // function to reset to initial state
    fun clearPin() {
        _pinState.update { it.copy(error = null, toastMessage = null) }
    }
}

