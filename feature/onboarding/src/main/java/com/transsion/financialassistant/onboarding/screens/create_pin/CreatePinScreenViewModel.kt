package com.transsion.financialassistant.onboarding.screens.create_pin

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreatePinScreenViewModel @Inject constructor(): ViewModel() {
    
    fun getGreetingBasedOnTime(): Int {
        val currentHour = LocalTime.now().hour
        return when (currentHour) {
            in 5..11 -> R.string.good_morning
            in 12..16 ->R.string.good_afternoon
            in 17..20 -> R.string.good_evening
            else -> R.string.good_night
        }
    }
}

