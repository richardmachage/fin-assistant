package com.transsion.financialassistant.onboarding.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import javax.inject.Inject

class OnboardingRepoImpl @Inject constructor(
    private val datastorePreferences: DatastorePreferences,
    private val sharedPreferences: SharedPreferences
) : OnboardingRepo {


    override fun hasCompletedOnboarding(): Boolean {
        val status = sharedPreferences.loadData(SharedPreferences.ONBOARDING_COMPLETED_KEY)
        return status?.let { true } ?: false
    }

    override fun setCompletedOnboarding() {
        sharedPreferences.saveData(
            key = SharedPreferences.ONBOARDING_COMPLETED_KEY,
            value = SharedPreferences.ONBOARDING_COMPLETED_KEY
        )
    }

    @SuppressLint("MissingPermission", "NewApi")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getMpesaNumbersOnDevice(
        context: Context,
        onSuccess: (phoneNumbers: List<String>) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        //check for READ PHONE_STATE PERMISSION  first and throw permission error if not granted
        if ((checkSelfPermission(
                context,
                android.Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED).not()
        ) {
            onFailure("Permission to read phone numbers not granted")
            return
        }

        try {
            val mpesaNumbers = mutableListOf<String>()
            val subscriptionManager =
                context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val availableSims = subscriptionManager.activeSubscriptionInfoList

            availableSims?.let {
                it.forEach { subscriptionInfo ->
                    val mcc = subscriptionInfo.mccString.toString()
                    val mnc = subscriptionInfo.mncString.toString()

                    //check if number is for safaricom
                    //check if number is for safaricom
                    when (isSafaricomNumber(mccMnc = "$mcc$mnc")) {
                        false -> {
                            //Not an mpesa number, so do nothing
                        }

                        true -> {
                            // is an mpesa number,add it to the list
                            val number =
                                subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId)
                            mpesaNumbers.add(number)
                        }

                    }
                }

                if (mpesaNumbers.isEmpty()) {
                    onFailure("No M-PESA numbers found")
                } else {
                    onSuccess(mpesaNumbers)
                }

            } ?: run {
                onFailure("No SIM cards found")
            }

        } catch (e: Exception) {
            onFailure(e.message.toString())
        }
    }

    private fun isSafaricomNumber(mccMnc: String): Boolean {
        return mccMnc == SAFARICOM_MCC_MNC
    }
}