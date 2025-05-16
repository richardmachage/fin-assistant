package com.transsion.financialassistant.data.repository.transaction.receive_pochi

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiDao
import javax.inject.Inject

class ReceivePochiRepoImpl @Inject constructor(
    private val receivePochiDao: ReceivePochiDao
): ReceivePochiRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertReceivePochiTransaction(
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
            val receivePochiEntity = parseReceivePochiMessage(message, thisPhone ?: "")

            // insert transaction to db
            receivePochiEntity?.let {
                receivePochiDao.insert(it)
                onSuccess()
            }?: run {
                return onFailure("Failed to parse receive Pochi message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }
}