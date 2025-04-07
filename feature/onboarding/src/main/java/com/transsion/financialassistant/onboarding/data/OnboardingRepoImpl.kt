package com.transsion.financialassistant.onboarding.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC
import com.transsion.financialassistant.data.SAFARICOM_MCC_MNC_0
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.repository.pin.PinRepoImpl
import com.transsion.financialassistant.data.repository.security.SecurityRepo
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import javax.inject.Inject

class OnboardingRepoImpl @Inject constructor(
    private val datastorePreferences: DatastorePreferences,
    private val sharedPreferences: SharedPreferences,
    private val securityRepo: SecurityRepo,
) : OnboardingRepo,
    PinRepoImpl(sharedPreferences = sharedPreferences, securityRepo = securityRepo) {
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
                    when (isSafaricomNumber(mccMnc = "$mcc$mnc")) {
                        false -> {
                            //Not an mpesa number, so do nothing
                        }

                        true -> {
                            // is an mpesa number,add it to the list
                            val number =
                                subscriptionManager.getPhoneNumber(subscriptionInfo.subscriptionId)
                            if (number.isNotBlank()) mpesaNumbers.add(number)
                        }

                    }
                }

                if (mpesaNumbers.isEmpty()) {
                    onFailure("No M-PESA numbers found. Please insert an MPESA SIM card.")
                } else {
                    onSuccess(mpesaNumbers)
                }

            } ?: run {
                onFailure("No active SIM cards detected. Please insert a Safaricom SIM card.")
            }

        } catch (e: Exception) {
            onFailure(e.message.toString())
        }
    }

    override suspend fun setMpesaNumber(
        mpesaNumber: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        try {

            if (mpesaNumber.isBlank()) throw Exception("Invalid M-PESA number")

            datastorePreferences.saveValue(
                key = DatastorePreferences.MPESA_NUMBER_KEY,
                value = mpesaNumber
            )
            onSuccess()
        } catch (e: Exception) {
            onFailure(e.message.toString())
        }
    }

    override suspend fun setPin(
        pin: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        super.setPin(pin, onSuccess, onFailure)
    }

    override fun verifyPin(
        pin: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {

        try {
            //retrieve correct pin from shared preferences
            val correctPin = sharedPreferences.loadData(SharedPreferences.PIN_KEY)
                ?: throw Exception("No PIN found")
            //decrypt the correct pin into the hashed value
            val decryptedPin = securityRepo.decryptData(correctPin)

            //hash the input pin
            val hashedInputPin = securityRepo.doHash(pin)

            val result = hashedInputPin.contentEquals(decryptedPin)
            onSuccess(result)

        } catch (e: Exception) {
            onFailure(e.message.toString())
        }

    }

    private fun isSafaricomNumber(mccMnc: String): Boolean {
        Log.d(
            "isSafaricomNumber",
            "is mccmnc : $mccMnc == safMncemcc: $SAFARICOM_MCC_MNC or $SAFARICOM_MCC_MNC_0"
        )
        return mccMnc == SAFARICOM_MCC_MNC || mccMnc == SAFARICOM_MCC_MNC_0
    }

}


fun addTwoNumbers(firstNum: Int, secondNumber: Int): Int? {
    //ask user to enter number one
    println("Enter first number")
    val a = readlnOrNull()?.toIntOrNull()
    //ask user to enter number two
    println("Enter second number")
    val b = readlnOrNull()?.toIntOrNull()

    //get the sum
    val sum = a ?: (0 + b!!) ?: 0
    //return the sum
    return sum
}