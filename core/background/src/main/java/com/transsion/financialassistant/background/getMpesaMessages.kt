package com.transsion.financialassistant.background

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import com.transsion.financialassistant.background.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import kotlin.time.Duration
import kotlin.time.measureTime


@SuppressLint("MissingPermission")
fun getMpesaMessages(
    filterValue: TransactionType,//String? = null,
    context: Context,
    getExecutionTime: (Duration) -> Unit,
    transactionRepo: TransactionRepo
): List<MpesaMessage> {

    val mpesaMessages = mutableListOf<MpesaMessage>()
    val projection = arrayOf(
        Telephony.Sms.BODY,
        Telephony.Sms.SUBSCRIPTION_ID
    )

    val selection = "${Telephony.Sms.ADDRESS} = ?"

    val selectionArgs = arrayOf("MPESA")

    val sortOrder = "${Telephony.Sms.DATE} DESC"

    return try {
        val cursor: Cursor? = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.use {
            val bodyColumn = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val subscriptionIdColumn = it.getColumnIndexOrThrow(Telephony.Sms.SUBSCRIPTION_ID)

            val timeTaken = measureTime {
                while (it.moveToNext()) {
                    // filterValue.let { value ->
                        val body = it.getString(bodyColumn)
                    val transactionType = transactionRepo.getTransactionType(body)

                    if (
                    //body.contains(value)
                        transactionType == filterValue
                    ) {
                            mpesaMessages.add(
                                MpesaMessage(
                                    body = it.getString(bodyColumn),
                                    subscriptionId = getReceivingAddress(
                                        context = context,
                                        subscriptionId = it.getInt(subscriptionIdColumn)
                                    ).toString()
                                )
                            )
                        }
                    //}
                    /*?: run {
                        mpesaMessages.add(
                            MpesaMessage(
                                body = it.getString(bodyColumn),
                                subscriptionId = it.getString(subscriptionIdColumn)
                            )
                        )
                    }*/

                }
            }
            Log.d("DurationInGetMessages", "Time taken: $timeTaken")
            getExecutionTime(timeTaken)
        }
        mpesaMessages
    } catch (e: Exception) {
        e.printStackTrace()
        //TODO handle errors if any
        getExecutionTime(Duration.ZERO)
        emptyList()
    }
    //return emptyList()
}