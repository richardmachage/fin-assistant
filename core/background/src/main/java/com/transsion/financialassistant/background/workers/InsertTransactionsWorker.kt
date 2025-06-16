package com.transsion.financialassistant.background.workers

import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.transsion.financialassistant.background.Repos
import com.transsion.financialassistant.background.broadcast_receivers.acceptedUnknownKeywords
import com.transsion.financialassistant.data.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.Metrics
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@HiltWorker
class InsertTransactionsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repos: Repos,
    private val transactionRepo: TransactionRepo,
    private val dataStore: DatastorePreferences,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val saveStrategyMap = saveStrategies(repos, context)

        val projection = arrayOf(
            Telephony.Sms.BODY,
            Telephony.Sms.SUBSCRIPTION_ID
        )

        val selection = "${Telephony.Sms.ADDRESS} = ?"

        val selectionArgs = arrayOf("MPESA")

        val sortOrder = "${Telephony.Sms.DATE} DESC"

        val cursor: Cursor? = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder

        )

        cursor?.use { thisCursor ->
            val bodyColumn = thisCursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val subscriptionIdColumn =
                thisCursor.getColumnIndexOrThrow(Telephony.Sms.SUBSCRIPTION_ID)

            val totalMessages =
                thisCursor.count.takeIf { it > 0 } ?: return@withContext Result.success()
            var unknownMessages = 0
            var acceptedUnknowns = 0
            var processedCount = 0

            thisCursor.moveToPosition(-1)

            while (thisCursor.moveToNext()) {

                val body = thisCursor.getString(bodyColumn)
                val subscriptionId = thisCursor.getString(subscriptionIdColumn)

                //branch here, only proceed for known types
                when (val transactionType = transactionRepo.getTransactionType(body)) {
                    TransactionType.UNKNOWN -> {
                        //check if it is an accepted unknown,
                        val isAcceptedUnknown = acceptedUnknownKeywords
                            .any { keyWord ->
                                body.contains(keyWord, ignoreCase = true)
                            }

                        if (isAcceptedUnknown.not()) {
                            //it is not an accepted unknown type
                            //insert to entity of unknowns,
                            unknownMessages += 1
                            repos.unknownRepo.insertUnknownTransaction(message = body)
                        } else {
                            acceptedUnknowns += 1
                        }
                    }

                    else -> {

                        val saveAction = saveStrategyMap[transactionType]

                        saveAction?.let {
                            val message = MpesaMessage(body = body, subscriptionId = subscriptionId)
                            try {
                                saveAction(message)
                            } catch (e: Exception) {
                                Log.e("InsertWorker", "Error saving: ${e.message}")
                            }

                        } ?: run {
                            Log.w("InsertWorker", "No save strategy for type: $transactionType")
                        }
                        processedCount += 1

                        val progress = processedCount.toFloat() / totalMessages.toFloat()

                        setProgressAsync(
                            workDataOf(
                                "progress" to progress,
                                "currentType" to transactionType.name
                            )
                        )
                    }
                }
            }

            Log.d(
                "WorkerReport",
                "Total messages: $totalMessages, Processed: $processedCount, Unknown: $unknownMessages, Accepted Unknowns: $acceptedUnknowns"
            )

            val metrics = Metrics(
                totalMessages = totalMessages,
                known = processedCount,
                accepted_unknown = acceptedUnknowns,
                rejected_unknown = unknownMessages
            )
            val jsonMetric = Json.encodeToString<Metrics>(metrics)
            dataStore.saveValue(
                key = DatastorePreferences.MESSAGE_PARSING_METRICS,
                value = jsonMetric
            )
        }

        Result.success()
    }

}


fun saveStrategies(
    repos: Repos,
    context: Context
): Map<TransactionType, suspend (MpesaMessage) -> Unit> = mapOf(

    TransactionType.REVERSAL_CREDIT to { message ->
        repos.reversalCreditRepo.insertReversalCreditTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("ReversalCreditRepo", it)
            }
        )
    },

    TransactionType.REVERSAL_DEBIT to { message ->
        repos.reversalDebitRepo.insertReversalDebitTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("ReversalDebitRepo", it)
            }
        )
    },

    TransactionType.FULIZA_PAY to { message ->
        repos.fulizaRepo.insertFulizaPayTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("FulizaPayRepo", it)
            }
        )
    },

    TransactionType.DEPOSIT to { message ->
        repos.depositRepo.insertDepositTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("DepositRepo", it)
            },
        )
    },

    TransactionType.WITHDRAWAL to { message ->
        repos.withdrawMoneyRepo.insertWithdrawMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("WithdrawMoneyRepo", it)
            })
    },

    TransactionType.SEND_MONEY to { message ->
        repos.sendMoneyRepo.insertSendMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("SendMoneyRepo", it)
            })
    },

    TransactionType.RECEIVE_MONEY to { message ->
        repos.receiveMoneyRepo.insertReceiveMoneyTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("ReceiveMoneyRepo", it)
            })
    },

    TransactionType.RECEIVE_POCHI to { message ->
        repos.receivePochiRepo.insertReceivePochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("ReceivePochiRepo", it)
            })
    },

    TransactionType.SEND_POCHI to { message ->
        repos.sendPochiRepo.insertSendPochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("SendPochiRepo", it)
            })
    },

    TransactionType.PAY_BILL to { message ->
        repos.payBillRepo.insertPayBillTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("PayBillRepo", it)
            })
    },

    TransactionType.BUY_GOODS to { message ->
        repos.buyGoodsRepo.insertBuyGoodsTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("BuyGoodsRepo", it)
            })
    },

    TransactionType.SEND_MSHWARI to { message ->
        repos.sendMshwariRepo.insertSendMshwariTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("SendMshwariRepo", it)
            })
    },

    TransactionType.RECEIVE_MSHWARI to { message ->
        repos.receiveMshwariRepo.insertReceiveMshwariTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("ReceiveMshwariRepo", it)
            })
    },

    TransactionType.AIRTIME_PURCHASE to { message ->
        repos.buyAirtime.insertBuyAirtimeTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("BuyAirtimeRepo", it)
            })
    },

    TransactionType.BUNDLES_PURCHASE to { message ->
        repos.bundlesPurchaseRepo.insertBundlesPurchaseTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("BundlesPurchaseRepo", it)
            })
    },

    TransactionType.MOVE_TO_POCHI to { message ->
        repos.moveToPochiRepo.insertMoveToPochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("MoveToPochiRepo", it)
            }
        )


    },

    TransactionType.MOVE_FROM_POCHI to { message ->
        repos.moveFromPochiRepo.insertMoveFromPochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("MoveFromPochiRepo", it)
            }
        )
    },

    TransactionType.SEND_MONEY_FROM_POCHI to { message ->
        repos.sendFromPochiRepo.insertSendFromPochiTransaction(
            message = message.body,
            context = context,
            subId = message.subscriptionId.toIntOrNull() ?: 0,
            onSuccess = {},
            onFailure = {
                Log.e("SendFromPochiRepo", it)
            }
        )
    },


    TransactionType.UNKNOWN to { _: MpesaMessage ->
        // No-op or maybe log it
        Log.w("InsertWorker", "Skipping UNKNOWN transaction")
    }

)


