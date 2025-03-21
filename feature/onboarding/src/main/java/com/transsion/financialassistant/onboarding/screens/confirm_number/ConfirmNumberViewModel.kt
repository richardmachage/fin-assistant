package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val onboardingRepo: OnboardingRepo
) : ViewModel() {
    private var _state = MutableStateFlow(ConfirmNumberScreenState())
    val state = _state.asStateFlow()

    /*  private val _mpesaNumbers = MutableStateFlow<List<String>>(emptyList())
      val mpesaNumbers: StateFlow<List<String>> = _mpesaNumbers

      private val _selectedNumber = MutableStateFlow<String?>(null)
      val selectedNumber: StateFlow<String?> = _selectedNumber

      private val _errorMessage = MutableStateFlow<String?>(null)
      val errorMessage: StateFlow<String?> = _errorMessage
  */
    private fun isLoading(value: Boolean) {
        _state.update {
            it.copy(isLoading = value)
        }
    }


    fun loadMpesaNumbers(context: Context) {
        isLoading(true)
        onboardingRepo.getMpesaNumbersOnDevice(
            context,
            onSuccess = { numbers ->
                _state.update { it.copy(mpesaNumbers = numbers) }
                isLoading(false)
            },
            onFailure = { error ->
                isLoading(false)
                //_errorMessage.value = error
                _state.update { it.copy(errorMessage = error) }
            }
        )
    }


    fun onSelectNumber(number: String) {
        _state.update {
            it.copy(selectedNumber = if (it.selectedNumber == number) null else number)
        }
    }

    fun onSaveSelectedNumber(number: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            onboardingRepo.setMpesaNumber(
                mpesaNumber = number,
                onSuccess = {
                    onSuccess()
                },
                onFailure = {
                    //show failed toast ama error pop up
                }
            )
        }

    }

}
