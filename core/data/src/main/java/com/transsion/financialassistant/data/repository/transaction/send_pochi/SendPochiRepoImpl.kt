package com.transsion.financialassistant.data.repository.transaction.send_pochi

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiDao
import javax.inject.Inject

class SendPochiRepoImpl @Inject constructor(
    private val sendPochiDao: SendPochiDao
): SendPochiRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertSendPochiTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String?
    ) {
        var thisPhone = phone

        try {
            // get receiving address
            if (thisPhone == null) {
                thisPhone = getReceivingAddress(context, subId)
            }

            // parse the message
            val sendPochiEntity = parseSendPochiMessage(message, thisPhone ?: "")

            // insert transaction to db
            sendPochiEntity?.let {
                sendPochiDao.insert(it)
                onSuccess()
            }?: run {
                // handle insert data into db errors
                return onFailure("Failed to parse send pochi message")
            }

        } catch (e: Exception){
            onFailure(e.message ?: "Unknown error")
        }
    }
}