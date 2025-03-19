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
    private val repository : OnboardingRepo
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
        repository.getMpesaNumbersOnDevice(
            context,
            onSuccess = {numbers -> _mpesaNumbers.value = numbers},
            onFailure = { error ->
                _errorMessage.value = error
            }
        )
    }

    fun selectNumber(number: String) {
        _selectedNumber.value = if (_selectedNumber.value == number) null else number
    }

    @SuppressLint("MissingPermission", "NewApi")
    fun getPhoneNumbers(
        context: Context,
        onSuccess: (List<String>) -> Unit,
        onFailure: () -> Unit
    ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
            ){
            onFailure()
            return
        }
        //get the phone numbers in the phone
        val mpesaNumbers = mutableListOf<String>()

        try {
            val subscriptionManager =
                context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val availableSims = subscriptionManager.activeSubscriptionInfoList ?: emptyList()

            availableSims.let {
                it.forEach { subscriptionInfo ->
                    val mcc = subscriptionInfo.mccString.toString()
                    val mnc = subscriptionInfo.mncString.toString()

                    //check if number is for safaricom
                    if (isSafaricomNumber("$mcc$mnc")){
                        subscriptionInfo.number?.let { number ->
                            if (number.isNotBlank()) mpesaNumbers.add(number)
                        }
                    }
        //                    when (isSafaricomNumber("$mcc$mnc")) {
        //                        false -> {
        //                            //Not an mpesa number, so do nothing
        //                        }
        //
        //                        true -> {
        //                            // is an mpesa number
        //                            // retrieve the number and add it to the list
        //                            val number =
        //                                subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId)
        //                            mpesaNumbers.add(number)
        //                        }
        //                    }

                }
                if (mpesaNumbers.isNotEmpty()){
                    onSuccess(mpesaNumbers)
                } else {
                    onFailure()
                }

                //onSuccess()

            } ?: run {
                //TODO no sim card found
            }
        } catch (e: Exception) {
            Log.e("ConfirmNumberViewModel", "Error Fetching Phone Numbers: ${e.message}")
            onFailure()
        }
    }
}


fun isSafaricomNumber(mccMnc: String): Boolean {
    return mccMnc == SAFARICOM_MCC_MNC
}