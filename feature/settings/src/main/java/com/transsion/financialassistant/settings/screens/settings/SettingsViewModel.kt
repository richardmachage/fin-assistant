package com.transsion.financialassistant.settings.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: DatastorePreferences
) : ViewModel() {

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferences.saveValue(DatastorePreferences.THEME_KEY, mode.name)
        }
    }

    fun getCurrentTheme() = preferences.getValue(
        DatastorePreferences.THEME_KEY,
        defaultValue = ThemeMode.SYSTEM.name
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ThemeMode.SYSTEM.name
    )

}