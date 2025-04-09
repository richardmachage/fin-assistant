package com.transsion.financialassistant.home.screens.all_transactions.filter_values.viewmodel

import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.home.model.all_transactions.FilterCategory
import com.transsion.financialassistant.home.model.all_transactions.FilterPeriod
import com.transsion.financialassistant.home.model.all_transactions.FilterSource
import com.transsion.financialassistant.home.model.all_transactions.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(): ViewModel() {
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    fun onSourceChanged(source: FilterSource) {
        _filterState.update { it.copy(source = source) }
    }

    fun onPeriodChanged(period: FilterPeriod) {
        _filterState.update { it.copy(period = period) }
    }

    fun toggleCategory(category: FilterCategory) {
        _filterState.update {
            val current = it.selectedCategories

            val updated = if (category in current) current - category else current + category
            it.copy(selectedCategories = updated)
        }
    }

    // reset filters
    fun resetFilters(){
        _filterState.value = FilterState() // Reset to Default
    }

    fun applyFilters(): FilterState{
        return _filterState.value
    }

}