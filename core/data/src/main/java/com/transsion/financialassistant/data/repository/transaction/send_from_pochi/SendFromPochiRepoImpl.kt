package com.transsion.financialassistant.data.repository.transaction.send_from_pochi

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_from_pochi.SendFromPochiDao
import javax.inject.Inject

class SendFromPochiRepoImpl @Inject constructor(
    private val sendFromPochiDao: SendFromPochiDao
) : SendFromPochiRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertSendFromPochiTransaction(
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
            val sendFromPochiEntity = parseSendFromPochiMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            sendFromPochiEntity?.let {
                sendFromPochiDao.insert(it)
            } ?: run {
                //Handle error state
                onFailure("Failed to parse send From Pochi message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error when inserting send From Pochi transaction")
        }
    }

}