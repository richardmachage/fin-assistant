package com.transsion.financialassistant.onboarding.screens.create_pin

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreatePinScreenViewModel @Inject constructor(
    private val repository: OnboardingRepo
): ViewModel() {
    private val _pinState = MutableStateFlow<PinState>(PinState.Idle)
    val pinState : StateFlow<PinState> = _pinState

    fun setUserPin(pin: String) {
        viewModelScope.launch {
            _pinState.value = PinState.Loading

            repository.setPin(
                pin = pin,
                onSuccess = {
                    _pinState.value = PinState.Success
                },
                onFailure = {
                    _pinState.value = PinState.Error(message = it)
                }
            )
        }
    }
}

