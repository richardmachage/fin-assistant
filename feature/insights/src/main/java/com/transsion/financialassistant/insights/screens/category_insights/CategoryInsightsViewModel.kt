package com.transsion.financialassistant.insights.screens.category_insights

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.insights.domain.InsightsRepo
import com.transsion.financialassistant.insights.model.InsightTimeline
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

    // val startDate = params.get<String>("startDate") ?: throw Exception("No start date provided")
    //val endDate = params.get<String>("endDate") ?: throw Exception("No end date provided")
    val insightTimeline =
        params.get<InsightTimeline>("timeLine") ?: throw Exception("No time line provided")
    val timeLine = insightTimeline.getTimeline().displayInfo
    private val transactionCategory = params.get<TransactionCategory>("transactionCategory")
        ?: throw Exception("No transaction category provided")



    private var _state = MutableStateFlow(CategoryInsightsScreenState())
    var state = _state.asStateFlow()


    val transactionCostGraphData = insightsRepo.getDataPointsForTransactionCost(insightTimeline)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val categoryGraphData = insightsRepo.getDataPointsForCategory(
        transactionType = getCategoryEnum(),
        insightTimeline = insightTimeline
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


    val listOfTransactionCosts = insightsRepo.getDataForTransactionCost(insightTimeline)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val listOfTransactions = insightsRepo.getDataForCategory(
        /*startDate = startDate,
        endDate = endDate,*/
        insightTimeline = insightTimeline,
        transactionType = getCategoryEnum(),
        transactionCategory = transactionCategory
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )


    private fun getCategoryEnum(): TransactionType {

        if (category == "transactionCost") return TransactionType.UNKNOWN

        TransactionType.entries.forEach {
            if (it.description == category) {
                Log.d("TAG", "getCategoryEnum: $it")
                return it
            }
        }
        throw Exception("Category  not found")
    }

}