package com.transsion.financialassistant.insights.screens.category_insights

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryInsightsViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(CategoryInsightsScreenState())
    var state = _state.asStateFlow()
}