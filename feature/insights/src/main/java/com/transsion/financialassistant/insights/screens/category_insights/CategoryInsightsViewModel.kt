package com.transsion.financialassistant.insights.screens.category_insights

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.insights.domain.InsightsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CategoryInsightsViewModel @Inject constructor(
    private val insightsRepo: InsightsRepo,
    params: SavedStateHandle
) : ViewModel() {

    val category = params.get<String>("category") ?: throw Exception("No category provided")
    val startDate = params.get<String>("startDate") ?: throw Exception("No start date provided")
    val endDate = params.get<String>("endDate") ?: throw Exception("No end date provided")
    val insightCategory =
        params.get<String>("insightCategory") ?: throw Exception("No insight category provided")


    private var _state = MutableStateFlow(CategoryInsightsScreenState())
    var state = _state.asStateFlow()


    val categoryGraphData = insightsRepo.getDataPointsForCategory(
        startDate = startDate,
        endDate = endDate,
        transactionType = getCategoryEnum()
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


    private fun getCategoryEnum(): TransactionType {
        TransactionType.entries.forEach {
            if (it.description == category) {
                return it
            }
        }
        throw Exception("Category  not found")
    }
}