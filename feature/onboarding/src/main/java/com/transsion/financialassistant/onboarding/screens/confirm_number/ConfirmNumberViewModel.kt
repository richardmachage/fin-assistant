package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionManager
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
}


fun isSafaricomNumber(mccMnc: String): Boolean {
    return mccMnc == SAFARICOM_MCC_MNC
}