package com.transsion.financialassistant.insights.screens.insights

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor() : ViewModel() {
    val state = MutableStateFlow(InsightsScreenState())
}