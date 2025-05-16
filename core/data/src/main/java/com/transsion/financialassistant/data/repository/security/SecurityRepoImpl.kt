package com.transsion.financialassistant.data.repository.security

import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.security.EncryptedData
import com.transsion.financialassistant.security.EncryptionManager
import com.transsion.financialassistant.security.HashingManager
import javax.inject.Inject

class SecurityRepoImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SecurityRepo {
    private fun getHashingSalt(): String {

        val salt = sharedPreferences.loadData(SharedPreferences.SALT_KEY)

        return salt ?: run {
            //create the salt
            val generatedSalt = HashingManager.saltToString(HashingManager.generateSalt())

            //save the salt to shared preferences
            sharedPreferences.saveData(
                key = SharedPreferences.SALT_KEY,
                value = generatedSalt
            )
            //return the salt
            generatedSalt

        }
    }

    /*override fun doHash(
        value: String,
        onSuccess: (hash: ByteArray) -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        try {
            val hash = HashingManager.hashData(
                data = value,
                salt = HashingManager.stringToSalt(getHashingSalt())
            )
            onSuccess(hash)
        } catch (e: Exception) {
            onFailure(e.message.toString())
        }
    }*/

    override fun doHash(value: String): ByteArray {
        return try {
            HashingManager.hashData(
                data = value,
                salt = HashingManager.stringToSalt(getHashingSalt())
            )
        } catch (e: Exception) {
            throw RuntimeException("Hashing failed: ${e.message}")
        }
    }

    override fun encryptData(data: ByteArray) = EncryptionManager.encryptData(data)

    override fun decryptData(data: String) = EncryptionManager.decryptData(
        encryptedData = EncryptedData(
            iv = sharedPreferences.loadData(SharedPreferences.IV_KEY)
                ?: throw IllegalArgumentException("No IV found"),
            data = data
        )
    )

}