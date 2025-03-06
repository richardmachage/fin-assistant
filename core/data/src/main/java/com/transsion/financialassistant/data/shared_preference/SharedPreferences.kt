package com.transsion.financialassistant.data.shared_preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("ApplySharedPref")
class SharedPreferences(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_FILE_KEY = "financialassistant.SHARED_PREFERENCES"
    }

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun deleteData(key: String) {
        sharedPreferences
            .edit()
            .remove(key)
            .commit()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().commit()
    }
}