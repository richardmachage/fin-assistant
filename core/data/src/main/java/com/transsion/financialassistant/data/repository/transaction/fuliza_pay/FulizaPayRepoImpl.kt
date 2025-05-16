package com.transsion.financialassistant.data.repository.transaction.fuliza_pay

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.fuliza_pay.FulizaPayDao
import javax.inject.Inject

class FulizaPayRepoImpl @Inject constructor(
    private val fulizaPayDao: FulizaPayDao
) : FulizaPayRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertFulizaPayTransaction(
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
            val fulizaPayEntity = parseFulizaPayMessage(message, thisPhone ?: "", isTest = false)

            // insert transaction to db
            fulizaPayEntity?.let {
                fulizaPayDao.insert(it)
                onSuccess()
            } ?: run {
                // handle insert data into db errors
                return onFailure("Failed to parse pay fuliza message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }


}