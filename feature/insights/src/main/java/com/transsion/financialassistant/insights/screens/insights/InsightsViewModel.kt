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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val insightsRepo: InsightsRepo
) : ViewModel() {
    private var _state = MutableStateFlow(InsightsScreenState())
    val state = _state.asStateFlow()

    val graphDataFlow = getGraphData()
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

    init {
        refreshMoneyInOutCardInfo()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getGraphData() = state
        .map { it.selector() }
        .distinctUntilChanged()
        .flatMapLatest { selection ->
            refreshMoneyInOutCardInfo()

            insightsRepo.getDataPoints(
                insightCategory = selection.insightCategory,
                startDate = selection.insightTimeline.getTimeline().startDate,
                endDate = selection.insightTimeline.getTimeline().endDate,
                transactionCategory = selection.transactionCategory,
                insightTimeline = selection.insightTimeline
            )
            // .onStart {  } TODO ->  implement loading state on the graph alone based on this
        }


    private fun getMoneyIn(
    ) {
        viewModelScope.launch {
            //so this one should fetch the data from the repo and update the state
            insightsRepo.getTotalMoneyIn(
                startDate = state.value.insightTimeline.getTimeline().startDate,
                endDate = state.value.insightTimeline.getTimeline().endDate
            ).apply {
                onSuccess { totalMoneyIn ->
                    _state.update { it.copy(moneyIn = totalMoneyIn.toString()) }
                }

                onFailure {
                    //TODO
                }
            }
        }
    }


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


    private fun getNumberOfTransactionsIn() {
        viewModelScope.launch {
            insightsRepo.getNumOfTransactionsIn(
                startDate = state.value.insightTimeline.getTimeline().startDate,
                endDate = state.value.insightTimeline.getTimeline().endDate
            ).apply {
                onSuccess { numberOfTransactions ->
                    _state.update { it.copy(transactionsIn = numberOfTransactions.toString()) }
                }
                onFailure {
                    //TODO
                }
            }
        }

    }

    private fun getMoneyOut() {
        viewModelScope.launch {
            insightsRepo.getTotalMoneyOut(
                startDate = state.value.insightTimeline.getTimeline().startDate,
                endDate = state.value.insightTimeline.getTimeline().endDate
            ).apply {
                onSuccess { totalMoneyOut ->
                    _state.update { it.copy(moneyOut = totalMoneyOut.toString()) }
                }
                onFailure {
                    //TODO
                }
            }
        }
    }

    private fun getNumberOfTransactionsOut(
    ) {
        viewModelScope.launch {
            insightsRepo.getNumOfTransactionsOut(
                startDate = state.value.insightTimeline.getTimeline().startDate,
                endDate = state.value.insightTimeline.getTimeline().endDate
            ).apply {
                onSuccess { numberOfTransactions ->
                    _state.update { it.copy(transactionsOut = numberOfTransactions.toString()) }
                }
                onFailure {
                    //TODO
                }
            }
        }
    }

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


    private fun refreshMoneyInOutCardInfo() {
        getMoneyIn()
        getMoneyOut()
        getNumberOfTransactionsIn()
        getNumberOfTransactionsOut()
        getTransactionCost()
    }

}


