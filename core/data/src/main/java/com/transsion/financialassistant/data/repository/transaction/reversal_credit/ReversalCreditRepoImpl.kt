package com.transsion.financialassistant.data.repository.transaction.reversal_credit

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.reversal_credit.ReversalCreditDao
import javax.inject.Inject

class ReversalCreditRepoImpl @Inject constructor(
    private val reversalCreditDao: ReversalCreditDao
) : ReversalCreditRepo, TransactionRepoImpl() {
    @RequiresPermission(Manifest.permission.READ_PHONE_NUMBERS)
    override suspend fun insertReversalCreditTransaction(
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
            val reversalCreditEntity = parseReversalCreditMessage(message, thisPhone ?: "")

            // insert transaction to db
            reversalCreditEntity?.let {
                reversalCreditDao.insert(it)
                onSuccess()
            } ?: run {
                return onFailure("Failed to parse reversal credit message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }

}