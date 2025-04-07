package com.transsion.financialassistant.home.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getMpesaMessagesByTransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepo
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepo
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepo
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepo
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.onboarding.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiveMoneyViewModel @Inject constructor(
    private val withdrawMoneyRepo: WithdrawMoneyRepo,
    private val sendMoneyRepo: SendMoneyRepo,
    private val receiveMoneyRepo: ReceiveMoneyRepo,
    private val sendMshwariRepo: SendMshwariRepo,
    private val paybilRepo: PayBillRepo,
    private val buyGoodsRepo: BuyGoodsRepo,
    private val buyAirtime: BuyAirtimeRepo,
    //private val depositRepo: DepositMoneyRepo,
    private val bundlesPurchaseRepo: BundlesPurchaseRepo,
    private val receiveMshwariRepo: ReceiveMshwariRepo,
    private val transactionRepo: TransactionRepo
): ViewModel() {
    private val _receivedMoneyTransactions = MutableStateFlow<List<SendMoneyEntity>>(emptyList())
    val reveivedMoneyTransactions: StateFlow<List<SendMoneyEntity>> = _receivedMoneyTransactions

    init {
        viewModelScope.launch {
            sendMoneyRepo.getAllSendMoneyTransactions().collect { transactions ->
                Log.d("ViewModelDB", "DB updated. Total transactions: ${transactions.size}")
                _receivedMoneyTransactions.value = transactions
            }
        }
    }

    // Fetch Received Money Transactions from DB
    fun fetchReceivedMoneyTransactions(context: Context) {
        viewModelScope.launch {

            TransactionType.entries.forEach{type->

                when(type){
                    TransactionType.DEPOSIT -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.DEPOSIT,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert Received Money Transaction and Refresh
//                        messages.forEach { mpesaMessage ->
//                            depositRepo.insertPayBillTransaction(
//                                message = mpesaMessage.body,
//                                context = context,
//                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
//                                onSuccess = {}, // UI updates automatically
//                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
//                                phone = mpesaMessage.subscriptionId
//                            )
//                        }
                    }
                    TransactionType.WITHDRAWAL -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.WITHDRAWAL,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert Withdraw Money Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            withdrawMoneyRepo.insertWithdrawMoneyTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // Send Money
                    TransactionType.SEND_MONEY -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.SEND_MONEY,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert send Money Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            sendMoneyRepo.insertSendMoneyTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // Receive Money
                    TransactionType.RECEIVE_MONEY -> {
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

                    // receive mshwari
                    TransactionType.RECEIVE_MSHWARI -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.RECEIVE_MSHWARI,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert Received Money Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            receiveMshwariRepo.insertReceiveMshwariTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // Sent to Paybill
                    TransactionType.PAY_BILL -> {
                            val messages = getMpesaMessagesByTransactionType(
                                filterValue = TransactionType.PAY_BILL,
                                context = context,
                                getExecutionTime = { duration ->
                                    Log.d("MpesaSync", "Fetched messages in $duration")
                                },
                                transactionRepo = transactionRepo
                            )

                            // Insert send to paybill Money Transaction and Refresh
                            messages.forEach { mpesaMessage ->
                                paybilRepo.insertPayBillTransaction(
                                    message = mpesaMessage.body,
                                    context = context,
                                    subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                    onSuccess = {}, // UI updates automatically
                                    onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                    phone = mpesaMessage.subscriptionId
                                )
                            }
                    }

                    // buy goods
                    TransactionType.BUY_GOODS -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.BUY_GOODS,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert Buy Goods Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            buyGoodsRepo.insertBuyGoodsTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // send to mshwari
                    TransactionType.SEND_MSHWARI -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.SEND_MSHWARI,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert send to mshwari Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            sendMshwariRepo.insertSendMshwariTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // airtime purchase
                    TransactionType.AIRTIME_PURCHASE -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.AIRTIME_PURCHASE,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert airtime purchase Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            buyAirtime.insertBuyAirtimeTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    // bundles purchase
                    TransactionType.BUNDLES_PURCHASE -> {
                        val messages = getMpesaMessagesByTransactionType(
                            filterValue = TransactionType.BUNDLES_PURCHASE,
                            context = context,
                            getExecutionTime = { duration ->
                                Log.d("MpesaSync", "Fetched messages in $duration")
                            },
                            transactionRepo = transactionRepo
                        )

                        // Insert bundles purchase Transaction and Refresh
                        messages.forEach { mpesaMessage ->
                            bundlesPurchaseRepo.insertBundlesPurchaseTransaction(
                                message = mpesaMessage.body,
                                context = context,
                                subId = 0, //mpesaMessage.subscriptionId.toInt(),
                                onSuccess = {}, // UI updates automatically
                                onFailure = { error -> Log.e("Error inserting transaction:", error) },
                                phone = mpesaMessage.subscriptionId
                            )
                        }
                    }

                    TransactionType.RECEIVE_POCHI -> {}

                    // send to pochi
                    TransactionType.SEND_POCHI -> {

                    }

                    TransactionType.UNKNOWN -> {
                        context.getString(R.string.unknown_transaction_type)
                    }
                }

            }


        }
    }


}