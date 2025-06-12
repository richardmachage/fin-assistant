package com.transsion.financialassistant.settings.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.repository.pin.PinRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: DatastorePreferences,
    private val pinRepo: PinRepo
) : ViewModel() {


    private var _isPinSet = MutableStateFlow(pinRepo.isPinSet())
    val isPinSet = _isPinSet.asStateFlow()

    var showDialog = mutableStateOf(false)

    init {
        refreshIsPinSet()
    }

    private fun refreshIsPinSet() {
        _isPinSet.value = pinRepo.isPinSet()
    }


    fun onDisableAuth() {
        pinRepo.deletePin()
        refreshIsPinSet()
    }

}