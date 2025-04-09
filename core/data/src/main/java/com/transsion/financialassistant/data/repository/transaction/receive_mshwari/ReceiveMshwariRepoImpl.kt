package com.transsion.financialassistant.data.repository.transaction.receive_mshwari

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariDao
import javax.inject.Inject

class ReceiveMshwariRepoImpl @Inject constructor(
    private val receiveMshwariDao: ReceiveMshwariDao
): ReceiveMshwariRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertReceiveMshwariTransaction(
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
            if (thisPhone == null){
                thisPhone = getReceivingAddress(context, subId)
            }

            // parse the message
            val receiveMshwariEntity = parseReceiveMshwariMessage(message, thisPhone?: "")

            // insert data into database
            receiveMshwariEntity?.let {
                receiveMshwariDao.insert(it)
                onSuccess()
            }?: run {
                onFailure("Failed to parse receive mshwari message")
            }

    }catch (e: Exception){
        onFailure(e.message ?: "Unknown error")
    }
    }
}