package com.transsion.financialassistant.onboarding.screens.confirm_number

import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC
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

}


fun isSafaricomNumber(mccMnc: String): Boolean {
    return mccMnc == SAFARICOM_MCC_MNC
}