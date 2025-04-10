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
        const val HAS_COMPLETED_PIN_SETUP = "has_completed_pin_setup"
        const val IS_MESSAGES_READ = "is_messages_read"

    }

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    // Save the entered PIN
    fun savePinSetupStatus(completed: Boolean) {
        sharedPreferences.edit {
            putBoolean(HAS_COMPLETED_PIN_SETUP, completed)
        }
    }

    fun isPinSetupCompleted(): Boolean {
        return sharedPreferences.getBoolean(HAS_COMPLETED_PIN_SETUP, false)
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