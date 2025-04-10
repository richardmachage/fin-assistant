package com.transsion.financialassistant.background.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.transsion.financialassistant.background.Repos
import com.transsion.financialassistant.data.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getMpesaMessagesByTransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


@HiltWorker
class InsertTransactionsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repos: Repos,
    private val transactionRepo: TransactionRepo
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val transactionResults = TransactionType.entries.map { type ->

                async {
                    type to fetchByType(type)
                }
            }.awaitAll().toMap()

            val totalTransactionsRetrieved = transactionResults.entries.sumOf { it.value.size }
            var processedCount = 0

            // now save transactions  to DB,
            transactionResults.entries.forEach { (type, messages) ->
                val saveAction = saveStrategies(repos = repos, context = context)[type]

                if (saveAction != null) {
                    messages.forEach { message ->

                        try {
                            saveAction(message)
                            Log.d(
                                "InsertWorker",
                                "Saved processed message: ${message.body} subId: ${message.subscriptionId}"
                            )
                        } catch (e: Exception) {
                            Log.e("Error saving transaction", e.message.toString())
                        }

                        processedCount += 1

                        val progress =
                            (processedCount.toFloat() / totalTransactionsRetrieved.toFloat()) //* 100

                        setProgressAsync(
                            workDataOf(
                                "progress" to progress,
                                "currentType" to type.name
                            )
                        )
                    }
                } else {
                    // Optionally log unsupported types
                    Log.w("InsertWorker", "No save strategy for type: $type")
                }
            }

            return@withContext Result.success()
        }
    }

    private fun fetchByType(type: TransactionType): List<MpesaMessage> {
        return getMpesaMessagesByTransactionType(
            transactionRepo = transactionRepo,
            context = context,
            getExecutionTime = {},
            filterValue = type
        )
    }

}


fun saveStrategies(
    repos: Repos,
    context: Context
): Map<TransactionType, suspend (MpesaMessage) -> Unit> = mapOf(
    TransactionType.DEPOSIT to { message ->
        repos.depositRepo.insertDepositTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {},
        )
    },
    TransactionType.WITHDRAWAL to { message ->
        repos.withdrawMoneyRepo.insertWithdrawMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.SEND_MONEY to { message ->
        repos.sendMoneyRepo.insertSendMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.RECEIVE_MONEY to { message ->
        repos.receiveMoneyRepo.insertReceiveMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.RECEIVE_POCHI to { message ->
        repos.receivePochiRepo.insertReceivePochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.SEND_POCHI to { message ->
        repos.sendPochiRepo.insertSendPochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.PAY_BILL to { message ->
        repos.payBillRepo.insertPayBillTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.BUY_GOODS to { message ->
        repos.buyGoodsRepo.insertBuyGoodsTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.SEND_MSHWARI to { message ->
        repos.sendMshwariRepo.insertSendMshwariTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.RECEIVE_MSHWARI to { message ->
        repos.receiveMshwariRepo.insertReceiveMshwariTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.AIRTIME_PURCHASE to { message ->
        repos.buyAirtime.insertBuyAirtimeTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.BUNDLES_PURCHASE to { message ->
        repos.bundlesPurchaseRepo.insertBundlesPurchaseTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {})
    },
    TransactionType.UNKNOWN to { _: MpesaMessage ->
        // No-op or maybe log it
        Log.w("InsertWorker", "Skipping UNKNOWN transaction")
    }

)


