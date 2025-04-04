package com.transsion.financialassistant.home.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getMpesaMessagesByTransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveMoneyViewModel @Inject constructor(
    private val receiveMoneyRepo: ReceiveMoneyRepo,
    private val transactionRepo: TransactionRepo
): ViewModel() {
    private val _receivedMoneyTransactions = MutableStateFlow<List<ReceiveMoneyEntity>>(emptyList())
    val reveivedMoneyTransactions: StateFlow<List<ReceiveMoneyEntity>> = _receivedMoneyTransactions

    init {
        viewModelScope.launch {
            receiveMoneyRepo.getAllReceiveMoneyTransactions().collect { transactions ->
                Log.d("ViewModelDB", "DB updated. Total transactions: ${transactions.size}")
                _receivedMoneyTransactions.value = transactions
            }
        }
    }

    // Fetch Received Money Transactions from DB
    fun fetchReceivedMoneyTransactions(context: Context) {
        viewModelScope.launch {
            val messages = getMpesaMessagesByTransactionType(
                filterValue = TransactionType.RECEIVE_MONEY,
                context = context,
                getExecutionTime = { duration ->
                    Log.d("MpesaSync", "Fetched messages in $duration")
                },
                transactionRepo = transactionRepo
            )

            // Insert Received Money Transaction and Refresh
            messages.forEach { mpesaMessage ->
                receiveMoneyRepo.insertReceiveMoneyTransaction(
                    message = mpesaMessage.body,
                    context = context,
                    subId = 0, //mpesaMessage.subscriptionId.toInt(),
                    onSuccess = {}, // UI updates automatically
                    onFailure = { error -> Log.e("Error inserting transaction:", error) },
                    phone = mpesaMessage.subscriptionId
                )
            }
        }
    }


}