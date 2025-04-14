package com.transsion.financialassistant.home.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recentTransactionsRepo: RecentTransactionRepo
) : ViewModel() {


    private var _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getTotalMoneyInAndOutToday()
    }
    val recentTransactions = recentTransactionsRepo.getRecentTransactions().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList<UnifiedTransaction>()
    )

    private fun getTotalMoneyInAndOutToday() {
        viewModelScope.launch {

            recentTransactionsRepo.getTotalMoneyIn().apply {
                onSuccess { result ->
                    _state.update {
                        it.copy(moneyIn = result.toString().formatAsCurrency())
                    }
                }
            }

            recentTransactionsRepo.getTotalMoneyOut().apply {
                onSuccess { result ->
                    _state.update {
                        it.copy(moneyOut = result.toString().formatAsCurrency())
                    }
                }
            }

        }
    }
}
