package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val onboardingRepo : OnboardingRepo
) : ViewModel() {
    private var _state = MutableStateFlow(ConfirmNumberScreenState())
    val state = _state.asStateFlow()

    private val _mpesaNumbers = MutableStateFlow<List<String>>(emptyList())
    val mpesaNumbers: StateFlow<List<String>> = _mpesaNumbers

    private val _selectedNumber = MutableStateFlow<String?>(null)
    val selectedNumber: StateFlow<String?> = _selectedNumber

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun isLoading(value: Boolean) {
        _state.update {
            it.copy(isLoading = value)
        }
    }


    fun loadMpesaNumbers(context: Context){
        isLoading(true)
        onboardingRepo.getMpesaNumbersOnDevice(
            context,
            onSuccess = {numbers ->

                _mpesaNumbers.value = numbers
                isLoading(false)
                        },
            onFailure = { error ->
                isLoading(false)
                _errorMessage.value = error
            }
        )
    }

    fun selectNumber(number: String) {
        _selectedNumber.value = if (_selectedNumber.value == number) null else number
    }

}
