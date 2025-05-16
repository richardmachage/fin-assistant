package com.transsion.financialassistant.home.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.home.R
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
import java.time.LocalTime
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recentTransactionsRepo: RecentTransactionRepo,
    private val preferences: DatastorePreferences,
) : ViewModel() {
    val hideBalance = preferences.getValue(DatastorePreferences.HIDE_BALANCE_KEY, false)
    private var _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()


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

    val numOfAllTransactions =
        state.map { it.insightCategory }
            .flatMapLatest {
                recentTransactionsRepo.getNumOfAllTransactions(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = 0
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

    val moneyInToday = state.map { it.insightCategory }
        .flatMapLatest {
            recentTransactionsRepo.getTotalMoneyIn(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )

    val moneyOutToday = state.map { it.insightCategory }
        .flatMapLatest {
            recentTransactionsRepo.getTotalMoneyOut(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )


    fun onInsightCategoryChange(insightCategory: InsightCategory) {
        _state.update {
            it.copy(insightCategory = insightCategory)
        }
    }


    // Salutation based on Time
    fun getGreetingBasedOnTime(context: Context): String {
        val currentHour = LocalTime.now().hour
        return when (currentHour) {
            in 5..11 -> context.getString(R.string.good_morning)
            in 12..16 -> context.getString(R.string.good_afternoon)
            in 17..20 -> context.getString(R.string.good_evening)
            else -> context.getString(R.string.good_night)
        }
    }


    fun onHideBalance(value: Boolean) {
        viewModelScope.launch {
            preferences.saveValue(DatastorePreferences.HIDE_BALANCE_KEY, value)
        }

    }

}
