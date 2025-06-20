package com.transsion.financialassistant.data.repository.transaction.send_till

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_till.SendTillDao
import javax.inject.Inject

class SendTillRepoImpl @Inject constructor(
    private val sendTillDao: SendTillDao
) : SendTillRepo, TransactionRepoImpl() {
    @RequiresPermission(Manifest.permission.READ_PHONE_NUMBERS)
    override suspend fun insertSendTillTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String?
    ) {
        var thisPhone = phone
        try {

            //get the receiving address
            if (thisPhone == null) {
                thisPhone = getReceivingAddress(context, subId)
            }

            //parse the message
            val sendTillEntity = parseSendTillMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            sendTillEntity?.let {
                sendTillDao.insert(it)
            } ?: run {
                //Handle error state
                onFailure("Failed to parse send from till message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error when inserting send from till transaction")
        }
    }
}