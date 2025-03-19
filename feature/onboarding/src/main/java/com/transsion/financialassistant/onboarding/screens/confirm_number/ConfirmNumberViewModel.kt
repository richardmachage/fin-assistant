package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmNumberViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private var _state = MutableStateFlow(ConfirmNumberScreenState())
    val state = _state.asStateFlow()

    private val _mpesaNumbers = MutableStateFlow<List<String>>(emptyList())
    val mpesaNumbers: StateFlow<List<String>> = _mpesaNumbers

    fun isLoading(value: Boolean) {
        _state.update {
            it.copy(isLoading = value)
        }
    }

    @SuppressLint("MissingPermission", "NewApi")
    fun getPhoneNumbers(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        //TODO check for permission first and throw permission error if not granted
        //get the phone numbers in the phone
        val mpesaNumbers = mutableListOf<String>()

        try {
            val subscriptionManager =
                context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val availableSims = subscriptionManager.activeSubscriptionInfoList

            availableSims?.let {
                it.forEach { subscriptionInfo ->
                    val mcc = subscriptionInfo.mccString.toString()
                    val mnc = subscriptionInfo.mncString.toString()

                    //check if number is for safaricom
                    when (isSafaricomNumber("$mcc$mnc")) {
                        false -> {
                            //Not an mpesa number, so do nothing
                        }

                        true -> {
                            // is an mpesa number
                            // retrieve the number and add it to the list
                            val number =
                                subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId)
                            mpesaNumbers.add(number)
                        }
                    }

                }

                onSuccess()

            } ?: run {
                //TODO no sim card found
            }
        } catch (e: Exception) {
            onFailure()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getPhoneDualNumbers() {
        viewModelScope.launch {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Handle Permission denial gracefully
                return@launch
            }

            val simNumbers = mutableListOf<String>()
            try {
                val subscriptionManager =
                    context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

                val availableSims = subscriptionManager.activeSubscriptionInfoList ?: emptyList()

                availableSims.forEach { subscriptionInfo ->
                    val mcc = subscriptionInfo.mccString ?: ""
                    val mnc = subscriptionInfo.mncString ?: ""

                    if (isSafaricomNumber("$mcc$mnc")) {
                        subscriptionInfo.number?.let { number ->
                            if (number.isNotBlank()) simNumbers.add(number)
                        }
                    }
                }
                _mpesaNumbers.value = simNumbers
            } catch (e: Exception){
                Log.e("ConfirmNumberViewModel", "Error Fetching Phone Numbers: ${e.message}")
            }
        }
    }
}


fun isSafaricomNumber(mccMnc: String): Boolean {
    return mccMnc == SAFARICOM_MCC_MNC
}