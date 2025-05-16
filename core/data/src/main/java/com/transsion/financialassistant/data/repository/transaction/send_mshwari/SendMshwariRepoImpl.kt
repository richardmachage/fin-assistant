package com.transsion.financialassistant.data.repository.transaction.send_mshwari

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariDao
import javax.inject.Inject

class SendMshwariRepoImpl @Inject constructor(
    private val sendMshwariDao: SendMshwariDao
): SendMshwariRepo, TransactionRepoImpl(){
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertSendMshwariTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String?
    ) {
        var thisPhone = phone

        try {

            //get receiving address
            if ( thisPhone == null){
                thisPhone = getReceivingAddress(context,subId)
            }

            // parse the message
            val sendMshwariEntity = parseSendMshwariMessage(message, thisPhone?: "")

            // insert transaction into the database
            sendMshwariEntity?.let {
                sendMshwariDao.insert(it)
                onSuccess()
            } ?: run {
                // handle insert data into db errors
                onFailure("Failed to parse send mshwari message")
            }

        } catch (e: Exception){
            onFailure(e.message ?: "Unknown error")
        }
    }


}