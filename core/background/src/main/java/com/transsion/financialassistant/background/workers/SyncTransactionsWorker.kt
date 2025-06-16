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
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SyncTransactionsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repos: Repos,
    private val transactionRepo: TransactionRepo,
    private val sharedPreferences: SharedPreferences
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val isFullSync = inputData.getBoolean("is_full_sync", false)

        val saveStrategyMap = saveStrategies(repos, context)
        val lastSyncDate = sharedPreferences.loadData(SharedPreferences.LAST_SYNC_TIMESTAMP)
            ?: System.currentTimeMillis().toString()
        val projection = arrayOf(
            Telephony.Sms.BODY,
            Telephony.Sms.SUBSCRIPTION_ID,
            Telephony.Sms.DATE
        )

        val selection =
            if (isFullSync) "${Telephony.Sms.ADDRESS} = ?" else "${Telephony.Sms.ADDRESS} = ?, ${Telephony.Sms.DATE} > ?"
        val selectionArgs = if (isFullSync) arrayOf("MPESA") else arrayOf("MPESA", lastSyncDate)

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
                                Log.e("SyncWorker", "Error saving: ${e.message}")
                            }
                        } ?: run {
                            Log.w("SyncWorker", "No save strategy for type: $transactionType")
                        }
                        processedCount += 1

                        val progress = processedCount.toFloat() / totalMessages.toFloat()

                        setProgressAsync(
                            workDataOf(
                                "progress" to progress,
                            )
                        )
                    }
                }
            }
        }

        sharedPreferences.saveData(
            SharedPreferences.LAST_SYNC_TIMESTAMP,
            System.currentTimeMillis().toString()
        )
        Result.success()
    }


}