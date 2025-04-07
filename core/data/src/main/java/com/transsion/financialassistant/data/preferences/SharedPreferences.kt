package com.transsion.financialassistant.data.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

@SuppressLint("ApplySharedPref")
class SharedPreferences(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)



    companion object {
        private const val PREFERENCES_FILE_KEY = "financialassistant.SHARED_PREFERENCES"
        const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
        const val PIN_KEY = "encrypted_hashed_pin"
        const val IV_KEY = "pin_iv"
        const val SALT_KEY = "pin_salt"

    }

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    // Save the entered PIN
    fun savePin(pin: String) {
        sharedPreferences.edit { putString(PIN_KEY, pin) }
    }



    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun deleteData(key: String) {
        sharedPreferences
            .edit(commit = true) {
                remove(key)
            }
    }

    fun clearAll() {
        sharedPreferences.edit(commit = true) { clear() }
    }
}