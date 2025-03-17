package com.transsion.financialassistant.main_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.background.getMpesaMessages
import com.transsion.financialassistant.background.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionRepo: TransactionRepo
) : ViewModel() {

    private var _timeTaken = MutableStateFlow(Duration.ZERO)
    val timeTaken = _timeTaken.asStateFlow()

    private var _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private var _messages = MutableStateFlow(emptyList<MpesaMessage>())
    val messages = _messages.asStateFlow()

    fun getTheMessages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.update { true }
            _messages.update {
                getMpesaMessages(
                    context = context,
                    filterValue = TransactionType.SEND_MONEY,
                    getExecutionTime = { dur ->
                        _timeTaken.update { dur }
                        _loadingState.update { false }
                    },
                    transactionRepo = transactionRepo
                )
            }
        }
    }
}