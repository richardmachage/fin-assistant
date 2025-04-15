package com.transsion.financialassistant.home.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recentTransactionsRepo: RecentTransactionRepo
) : ViewModel() {


    private var _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getTotalMoneyInAndOutToday()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recentTransactions =
        state.map { it.insightCategory }
            .flatMapLatest {
                recentTransactionsRepo.getRecentTransactions(it)
            }

            .stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
    )

    val mpesaBalance = state.map { it.insightCategory }
        .flatMapLatest {
            recentTransactionsRepo.getMpesaBalance(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
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

    fun onInsightCategoryChange(insightCategory: InsightCategory) {
        _state.update {
            it.copy(insightCategory = insightCategory)
        }
    }
}
