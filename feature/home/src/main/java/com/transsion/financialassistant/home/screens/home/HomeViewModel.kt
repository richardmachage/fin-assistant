package com.transsion.financialassistant.home.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.transsion.financialassistant.data.room.db.UnifiedTransaction
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import com.transsion.financialassistant.home.model.toRecentsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recentTransactionsRepo: RecentTransactionRepo
) : ViewModel() {

    private var _recents = MutableStateFlow(HomeScreenState())
    val recents = _recents.asStateFlow()

    val recentTransactions = recentTransactionsRepo.getRecentTransactions().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList<UnifiedTransaction>()
    )
}
