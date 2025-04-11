package com.transsion.financialassistant.home.screens.all_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.transsion.financialassistant.home.domain.AllTransactionsRepo
import com.transsion.financialassistant.home.screens.all_transactions.filter.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    private val allTransactionsRepo: AllTransactionsRepo
): ViewModel() {
    private var _state = MutableStateFlow(AllTransactionsScreenState())
    val state = _state.asStateFlow()

    private var _filters = MutableStateFlow(FilterState())
    val filters = _filters.asStateFlow()

    init {
        refreshMoneyInOutCardInfo()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val filterResults = filters
        .flatMapLatest {
            allTransactionsRepo.getAllTransactions()
                .cachedIn(viewModelScope)
        }

    private fun getMoneyIn() {
        viewModelScope.launch {
            allTransactionsRepo.getTotalMoneyIn()
                .onSuccess { totalMoneyIn ->
                    _state.update { it.copy(moneyIn = totalMoneyIn.toString()) }
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

    private fun getMoneyOut(){
        viewModelScope.launch {
            allTransactionsRepo.getTotalMoneyOut()
                .onSuccess { totalMoneyOut ->
                    _state.update { it.copy(moneyOut = totalMoneyOut.toString()) }
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

    private fun refreshMoneyInOutCardInfo(){
        getMoneyIn()
        getMoneyOut()
        getNumberOfTransactionsIn()
        getNumberOfTransactionsOut()
    }
}