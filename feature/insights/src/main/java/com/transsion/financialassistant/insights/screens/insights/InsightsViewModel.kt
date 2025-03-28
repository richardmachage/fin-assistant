package com.transsion.financialassistant.insights.screens.insights

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor() {
    val state = MutableStateFlow(InsightsScreenState())
}