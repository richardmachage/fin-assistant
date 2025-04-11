package com.transsion.financialassistant.home.screens.all_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.home.screens.all_transactions.domain.AllTransactionsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    private val allTransactionsRepo: AllTransactionsRepo
): ViewModel() {
    private var _state = MutableStateFlow(AllTransactionsScreenState())
    val state = _state.asStateFlow()

    init {
        refreshMoneyInOutCardInfo()
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