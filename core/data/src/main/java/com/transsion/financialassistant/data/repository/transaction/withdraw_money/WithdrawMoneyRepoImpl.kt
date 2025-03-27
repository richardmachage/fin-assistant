package com.transsion.financialassistant.data.repository.transaction.withdraw_money

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyDao
import javax.inject.Inject

class WithdrawMoneyRepoImpl @Inject constructor(
    private val withdrawMoneyDao: WithdrawMoneyDao
): WithdrawMoneyRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertWithdrawMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String?
    ) {
        var thisPhone = phone

        try {

            // get the receiving address
            if (thisPhone == null) {
                thisPhone = getReceivingAddress(context, subId)
            }

            // parse the message
            val withdrawMoneyEntity = parseWithdrawMoneyMessage(message, thisPhone ?: "")

            // insert the transaction into the database
            withdrawMoneyEntity?.let {
                withdrawMoneyDao.insert(it)
                onSuccess()

            } ?: run {
                // Handle error state
                onFailure("Failed to parse withdraw money message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }

    }
}

