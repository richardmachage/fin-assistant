package com.transsion.financialassistant.home.screens.all_transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.home.domain.AllTransactionsRepo
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val allTransactionsRepo: AllTransactionsRepo,
    private val preferences: DatastorePreferences
) : ViewModel() {
    val hideBalance = preferences.getValue(DatastorePreferences.HIDE_BALANCE_KEY, false)

    private val passedInsightCategory =
        savedStateHandle.get<InsightCategory>("insightCategory") ?: InsightCategory.PERSONAL
    private var _state =
        MutableStateFlow(AllTransactionsScreenState(insightCategory = passedInsightCategory))
    val state = _state.asStateFlow()

    private var _filters = MutableStateFlow(FilterState())
    val filters = _filters.asStateFlow()

    init {
        refreshMoneyInOutCardInfo()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val filterResults =
        combine(filters, state.map { it.insightCategory }) { filters, insightCategory ->
            filters to insightCategory
        }
            .flatMapLatest { (filters, insightCategory) ->
                when (insightCategory) {
                    InsightCategory.PERSONAL -> allTransactionsRepo.getAllTransactions(filters)
                    InsightCategory.BUSINESS -> allTransactionsRepo.getAllBusinessTransactions(
                        filters
                    )
                        .map {
                            it.map { bussinessTransaction ->
                                UnifiedTransactionPersonal(
                                    transactionCode = bussinessTransaction.transactionCode,
                                    phone = bussinessTransaction.phone,
                                    amount = bussinessTransaction.amount,
                                    date = bussinessTransaction.date,
                                    time = bussinessTransaction.time,
                                    name = bussinessTransaction.name,
                                    transactionCost = bussinessTransaction.transactionCost,
                                    mpesaBalance = bussinessTransaction.mpesaBalance,
                                    transactionCategory = bussinessTransaction.transactionCategory,
                                    transactionType = bussinessTransaction.transactionType
                                )
                            }
                        }
                }
                    .cachedIn(viewModelScope)
            }

    private fun getMoneyIn() {
        viewModelScope.launch {
            allTransactionsRepo.getTotalMoneyIn()
                .onSuccess { totalMoneyIn ->
                    _state.update { it.copy(moneyIn = totalMoneyIn.toString().formatAsCurrency()) }
                }
                .onFailure {
                    //TODO
                }
        }
    }

    fun onChangeFilters(filterState: FilterState) {
        _filters.update { filterState }
    }

    private fun getNumberOfTransactionsIn() {
        viewModelScope.launch {
            allTransactionsRepo.getNumOfTransactionsIn()
                .onSuccess { numberOfTransactions ->
                    _state.update { it.copy(transactionsIn = numberOfTransactions.toString()) }
                }
                .onFailure {
                    // TODO
                }
        }
    }

    private fun getMoneyOut() {
        viewModelScope.launch {
            allTransactionsRepo.getTotalMoneyOut()
                .onSuccess { totalMoneyOut ->
                    _state.update {
                        it.copy(
                            moneyOut = totalMoneyOut.toString().formatAsCurrency()
                        )
                    }
                }
                .onFailure {
                    // TODO
                }

        }
    }

    private fun getNumberOfTransactionsOut() {
        viewModelScope.launch {
            allTransactionsRepo.getNumOfTransactionsOut()
                .onSuccess { numberOfTransactions ->
                    _state.update { it.copy(transactionsOut = numberOfTransactions.toString()) }
                }
                .onFailure {
                    //TODO
                }
        }
    }

    fun onInsightCategoryChange(insightCategory: InsightCategory) {
        _state.update { it.copy(insightCategory = insightCategory) }
        refreshMoneyInOutCardInfo()
    }

    private fun refreshMoneyInOutCardInfo() {
        getMoneyIn()
        getMoneyOut()
        getNumberOfTransactionsIn()
        getNumberOfTransactionsOut()
    }

    fun onHideBalance(value: Boolean) {
        viewModelScope.launch {
            preferences.saveValue(DatastorePreferences.HIDE_BALANCE_KEY, value)
        }

    }
}