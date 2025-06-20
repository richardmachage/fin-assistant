package com.transsion.financialassistant.data.repository.transaction.receive_till

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.receive_till.ReceiveTillDao
import javax.inject.Inject

class ReceiveTillRepoImpl @Inject constructor(
    private val receiveTillDao: ReceiveTillDao
) : ReceiveTillRepo, TransactionRepoImpl() {
    @RequiresPermission(Manifest.permission.READ_PHONE_NUMBERS)
    override suspend fun receiveTillTransaction(
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
            val receiveTillEntity = parseReceiveTillMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            receiveTillEntity?.let {
                receiveTillDao.insertReceiveTill(it)
            } ?: run {
                //Handle error state
                onFailure("Failed to parse receiveTill message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error when inserting receive money in till transaction")
        }
    }
}