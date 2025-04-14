package com.transsion.financialassistant.home.screens.home

import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.home.screens.all_transactions.AllTransactionsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(AllTransactionsScreenState())
    val state = _state.asStateFlow()

    fun getMoneyIn(){

    }
}