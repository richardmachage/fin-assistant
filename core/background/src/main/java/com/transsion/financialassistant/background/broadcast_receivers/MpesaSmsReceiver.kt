package com.transsion.financialassistant.background.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms
import android.util.Log
import com.transsion.financialassistant.background.Repos
import com.transsion.financialassistant.background.workers.saveStrategies
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MpesaSmsReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repos: Repos

    @Inject
    lateinit var transactionRepo: TransactionRepo

    private val TAG = "MpesaSmsReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "broadcast fired")

        var subId: Int? = null

        Sms.Intents.getMessagesFromIntent(intent)?.let {
            subId = intent?.extras?.getInt("subscription", -1) ?: -1
            Log.d(TAG, "subscription Id = $subId")
        }

        val mpesaMessage = MpesaMessage(subscriptionId = subId.toString(), body = "")
        var originatingAddress: String? = null

        if (intent?.action == Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            bundle?.let {
                val messages = Sms.Intents.getMessagesFromIntent(intent)
                val fullMessage = StringBuilder()
                for (sms in messages) {
                    fullMessage.append(sms.messageBody)
                    originatingAddress = sms.originatingAddress.toString()
                }
                mpesaMessage.body = fullMessage.toString()

                Log.d(
                    TAG,
                    "SMS received from $originatingAddress: ${mpesaMessage.body} subId = ${mpesaMessage.subscriptionId}"
                )
            }

        }

        if (originatingAddress == "MPESA") {
            CoroutineScope(Dispatchers.IO).launch {
                //clear the cache
                AppCache.clear()

                //insert message to DB
                when (val transactionType = transactionRepo.getTransactionType(mpesaMessage.body)) {
                    TransactionType.UNKNOWN -> {

                        //check if it is an accepted unknown,
                        val isAcceptedUnknown = acceptedUnknownKeywords
                            .any { keyWord ->
                                mpesaMessage.body.contains(keyWord, ignoreCase = true)
                            }

                        if (isAcceptedUnknown.not()) {
                            //it is not an accepted unknown type
                            //insert to entity of unknowns,

                            repos.unknownRepo.insertUnknownTransaction(message = mpesaMessage.body)
                        }

                    }

                    else -> {
                        val saveAction =
                            saveStrategies(repos = repos, context = context)[transactionType]

                        saveAction?.let {
                            try {
                                saveAction(mpesaMessage)
                                Log.d(
                                    "InsertWorker",
                                    "Saved processed message: ${mpesaMessage.body} subId: ${mpesaMessage.subscriptionId}"
                                )
                            } catch (e: Exception) {
                                Log.e("Error saving transaction", e.message.toString())
                            }
                        }
                    }
                }

            }
        }

    }
}


data class ReceiverMessage(
    var body: String = "",
    var originatingAddress: String = "",
    var receivingAddress: String? = null
)


val acceptedUnknownKeywords = listOf(
    "failed",
    "insufficient",
    "unable",
    "changed to dormant",
    "not joined",
    "currently underway",
    "cancelled",
    "invalid input",
    "Your account balance was"
)
