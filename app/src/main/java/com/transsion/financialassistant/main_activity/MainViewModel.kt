package com.transsion.financialassistant.main_activity

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.background.getMpesaMessagesByTransactionType
import com.transsion.financialassistant.background.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

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
                getMpesaMessagesByTransactionType(
                    context = context,
                    getExecutionTime = { dur ->
//                        this.launch(Dispatchers.Main) {
                        Log.d("DurationInVM", "Time taken: $it")

                        _timeTaken.update { dur }
                            _loadingState.update { false }

                    },
                    transactionType = TransactionType.SEND_MSHWARI
                )
            }
        }
    }
}