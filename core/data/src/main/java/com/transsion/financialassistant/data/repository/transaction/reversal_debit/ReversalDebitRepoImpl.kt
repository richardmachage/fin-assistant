package com.transsion.financialassistant.data.repository.transaction.reversal_debit

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.reversal_debit.ReversalDebitDao
import javax.inject.Inject

class ReversalDebitRepoImpl @Inject constructor(
    private val reversalDebitDao: ReversalDebitDao
) : ReversalDebitRepo, TransactionRepoImpl() {
    @RequiresPermission(Manifest.permission.READ_PHONE_NUMBERS)
    override suspend fun insertReversalDebitTransaction(
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
            val reversalDebitEntity = parseReversalDebitMessage(message, thisPhone ?: "")

            // insert transaction to db
            reversalDebitEntity?.let {
                reversalDebitDao.insert(it)
                onSuccess()
            } ?: run {
                return onFailure("Failed to parse reversal debit message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }

}