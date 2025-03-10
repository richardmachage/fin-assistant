package com.transsion.financialassistant.onboarding.screens.confirm_number

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(ConfirmNumberScreenState())
    val state = _state.asStateFlow()

    fun isLoading(value: Boolean) {
        _state.update {
            it.copy(isLoading = value)
        }
    }

    fun getPhoneNumbers() {
        //get the phone numbers in the phone
    }
}