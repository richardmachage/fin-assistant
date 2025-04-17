package com.transsion.financialassistant.insights.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.insights.domain.InsightsRepo
import com.transsion.financialassistant.insights.model.InsightTimeline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class InsightsViewModel @Inject constructor(
    private val insightsRepo: InsightsRepo
) : ViewModel() {
    private var _state = MutableStateFlow(InsightsScreenState())
    val state = _state.asStateFlow()

    val graphDataFlow = //getGraphData()
        combine(
            state.map { it.insightCategory },
            state.map { it.insightTimeline },
            state.map { it.transactionCategory }
        ) { insightCategory, insightTimeline, transactionCategory ->
            Triple(insightCategory, insightTimeline, transactionCategory)
        }.distinctUntilChanged()
            .flatMapLatest {
                insightsRepo.getDataPoints(
                    insightCategory = it.first,
                    transactionCategory = it.third,
                    insightTimeline = it.second
                )
            }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val categoryDistributionFlow = insightsRepo.categoryDistributionFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getGraphData() = state
        .map { it.selector() }
        .distinctUntilChanged()
        .flatMapLatest { selection ->

            insightsRepo.getDataPoints(
                insightCategory = selection.insightCategory,
                transactionCategory = selection.transactionCategory,
                insightTimeline = selection.insightTimeline
            )
            // .onStart {  } TODO ->  implement loading state on the graph alone based on this
        }


    val moneyInFlow = combine(
        state.map { it.insightCategory },
        state.map { it.insightTimeline }) { insightCategory, insightTimeline ->
        insightCategory to insightTimeline
    }
        .distinctUntilChanged()
        .flatMapLatest {

            insightsRepo.getTotalMoneyIn(
                startDate = it.second.getTimeline().startDate,
                endDate = it.second.getTimeline().endDate,
                insightCategory = it.first

            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )

    val moneyOutFlow = combine(
        state.map { it.insightCategory },
        state.map { it.insightTimeline }) { insightCategory, insightTimeline ->
        insightCategory to insightTimeline
    }
        .distinctUntilChanged()
        .flatMapLatest {

            insightsRepo.getTotalMoneyOut(
                startDate = it.second.getTimeline().startDate,
                endDate = it.second.getTimeline().endDate,
                insightCategory = it.first
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )


    private fun getTransactionCost() {
        viewModelScope.launch {
            insightsRepo.getTotalTransactionCost(
                startDate = state.value.insightTimeline.getTimeline().startDate,
                endDate = state.value.insightTimeline.getTimeline().endDate
            ).apply {
                onSuccess { totalTransactionCost ->
                    _state.update { it.copy(totalTransactionCost = totalTransactionCost.toString()) }
                }
                onFailure {
                    //TODO
                }
            }
        }
    }

    val numOfTransactionsInFlow = combine(
        state.map { it.insightCategory },
        state.map { it.insightTimeline }) { insightCategory, insightTimeline ->
        insightCategory to insightTimeline
    }.distinctUntilChanged()
        .flatMapLatest {
            insightsRepo.getNumOfTransactionsIn(
                startDate = it.second.getTimeline().startDate,
                endDate = it.second.getTimeline().endDate,
                insightCategory = it.first
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )


    val numOfTransactionsOutFlow = combine(
        state.map { it.insightCategory },
        state.map { it.insightTimeline }) { insightCategory, insightTimeline ->
        insightCategory to insightTimeline
    }
        .distinctUntilChanged()
        .flatMapLatest {
            insightsRepo.getNumOfTransactionsOut(
                startDate = it.second.getTimeline().startDate,
                endDate = it.second.getTimeline().endDate,
                insightCategory = it.first
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0
        )


    fun switchTransactionCategory(transactionCategory: TransactionCategory) {
        _state.update { it.copy(transactionCategory = transactionCategory) }
    }

    fun switchInsightCategory(insightCategory: InsightCategory) {
        _state.update { it.copy(insightCategory = insightCategory) }
    }

    fun switchInsightTimeline(insightTimeline: InsightTimeline) {
        _state.update { it.copy(insightTimeline = insightTimeline) }
    }


    private data class GraphSelection(
        val transactionCategory: TransactionCategory,
        val insightCategory: InsightCategory,
        val insightTimeline: InsightTimeline
    )

    private fun InsightsScreenState.selector(): GraphSelection =
        GraphSelection(transactionCategory, insightCategory, insightTimeline)


}


