package com.transsion.financialassistant.data.repository.pin

import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.repository.security.SecurityRepo
import javax.inject.Inject

open class PinRepoImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val securityRepo: SecurityRepo
) : PinRepo {
    override suspend fun setPin(
        pin: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        try {

            if (pin.isBlank()) throw Exception("Invalid PIN")

            //hash pin
            val hashedPin = securityRepo.doHash(pin)
            //encrypt the hashed pin
            val encryptedData = securityRepo.encryptData(hashedPin)
            //save encrypted pin to shared preferences
            sharedPreferences.saveData(
                key = SharedPreferences.PIN_KEY,
                value = encryptedData.data
            )
            //save iv to shared preferences
            sharedPreferences.saveData(
                key = SharedPreferences.IV_KEY,
                value = encryptedData.iv
            )
            onSuccess()
        } catch (e: Exception) {
            onFailure(e.message.toString())
        }
    }

    override fun isPinSet(): Boolean {
        return sharedPreferences.loadData(SharedPreferences.PIN_KEY)?.let {
            true
        } ?: false
    }
}