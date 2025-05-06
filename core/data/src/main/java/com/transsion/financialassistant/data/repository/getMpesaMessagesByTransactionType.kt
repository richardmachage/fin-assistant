package com.transsion.financialassistant.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import android.util.Log
import com.transsion.financialassistant.data.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import kotlin.time.Duration
import kotlin.time.measureTime

/**
 *  * This function queries the SMS content provider for messages sent from "MPESA",
 *  * filters them by the specified [filterValue] (i.e., transaction type),
 *  * and maps them into a list of [MpesaMessage] objects.
 *  *
 */
@SuppressLint("MissingPermission")
fun getMpesaMessagesByTransactionType(
    filterValue: TransactionType,
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
}


fun getDateMessageReceived(
    context: Context,
    transactionCode: String,
): Result<Long> {

    return try {
        val selection = "${Telephony.Sms.ADDRESS} = ? AND ${Telephony.Sms.BODY} LIKE ?"
        val selectionArgs = arrayOf("MPESA", "%$transactionCode%")

        val projection = arrayOf(
            Telephony.Sms.DATE,
        )

        val cursor: Cursor? = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor.use { cu ->
            val present = cu?.moveToFirst() ?: false

            return if (present) {
                val dateColumn = cu?.getColumnIndexOrThrow(Telephony.Sms.DATE)

                dateColumn?.let { column ->
                    Result.success(cu.getLong(column))
                } ?: Result.failure(Exception("Message for this transaction not found"))
            } else {
                Result.failure(Exception("Message for this transaction not found"))
            }
        }
    } catch (e: Exception) {
        Result.failure(Exception("Message for this transaction not found"))

    }
}


fun getMessageForTransaction(context: Context, transactionCode: String): Result<String> {

    return try {
        val selection = "${Telephony.Sms.ADDRESS} = ? AND ${Telephony.Sms.BODY} LIKE ?"
        val selectionArgs = arrayOf("MPESA", "%$transactionCode%")

        val projection = arrayOf(
            Telephony.Sms.BODY,
        )

        val cursor: Cursor? = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor.use { cu ->

            val present = cu?.moveToFirst() ?: false

            return if (present) {
                val bodyColumn = cu?.getColumnIndexOrThrow(Telephony.Sms.BODY)

                val message = bodyColumn?.let { column ->
                    cu.getString(column)
                } ?: "Message for this transaction not found"
                Result.success(message)
            } else {
                Result.failure(Exception("Message for this transaction not found"))
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}