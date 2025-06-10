package com.transsion.financialassistant.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "financialassistant.DATASTORE")

class DatastorePreferences(private val context: Context) {

    companion object {
        val HIDE_BALANCE_KEY = booleanPreferencesKey("hide_balance_key")
        val MPESA_NUMBER_KEY = stringPreferencesKey("mpesa_number_key")
        val LANGUAGE_KEY = stringPreferencesKey("language_key")
        val MESSAGE_PARSING_METRICS = stringPreferencesKey("message_parsing_metrics")
        val THEME_KEY = stringPreferencesKey("theme_key")

    }

    private object PreferencesKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val PURPOSE = stringPreferencesKey("purpose")
        val PERSONAL_EXPENSES = stringPreferencesKey("personal_expenses")
        val BUSINESS_TYPE = stringPreferencesKey("business_type")
        val BUSINESS_EXPENSES = stringPreferencesKey("business_expenses")
        val PAYMENT_METHOD = stringPreferencesKey("payment_method")
    }

    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }


    fun <T> getValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    suspend fun <T> deleteValue(key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Expose specific methods to save values using predefined keys
    suspend fun saveOnboardingCompleted(completed: Boolean) {
        saveValue(PreferencesKeys.ONBOARDING_COMPLETED, completed)
    }

    suspend fun savePurpose(purpose: String) {
        saveValue(PreferencesKeys.PURPOSE, purpose)
    }

    suspend fun savePersonalExpenses(expenses: String) {
        saveValue(PreferencesKeys.PERSONAL_EXPENSES, expenses)
    }

    suspend fun saveBusinessDetails(type: String, expenses: String, paymentMethod: String) {
        saveValue(PreferencesKeys.BUSINESS_TYPE, type)
        saveValue(PreferencesKeys.BUSINESS_EXPENSES, expenses)
        saveValue(PreferencesKeys.PAYMENT_METHOD, paymentMethod)
    }

    fun getOnboardingCompleted(defaultValue: Boolean): Flow<Boolean> {
        return getValue(PreferencesKeys.ONBOARDING_COMPLETED, defaultValue)
    }

    fun getPurpose(defaultValue: String): Flow<String> {
        return getValue(PreferencesKeys.PURPOSE, defaultValue)
    }

    fun getPersonalExpenses(defaultValue: String): Flow<String> {
        return getValue(PreferencesKeys.PERSONAL_EXPENSES, defaultValue)
    }

    fun getBusinessType(defaultValue: String): Flow<String> {
        return getValue(PreferencesKeys.BUSINESS_TYPE, defaultValue)
    }

    fun getBusinessExpenses(defaultValue: String): Flow<String> {
        return getValue(PreferencesKeys.BUSINESS_EXPENSES, defaultValue)
    }

    fun getPaymentMethod(defaultValue: String): Flow<String> {
        return getValue(PreferencesKeys.PAYMENT_METHOD, defaultValue)
    }
}

@Serializable
data class Metrics(
    val totalMessages: Int = 0,
    val known: Int = 0,
    val accepted_unknown: Int = 0,
    val rejected_unknown: Int = 0,
)