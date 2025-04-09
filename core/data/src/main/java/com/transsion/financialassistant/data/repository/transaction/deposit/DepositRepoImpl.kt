package com.transsion.financialassistant.data.repository.transaction.deposit

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyDao
import javax.inject.Inject

class DepositRepoImpl @Inject constructor(
    private val depositMoneyDao: DepositMoneyDao
): DepositRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertDepositTransaction(
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
            val depositMoneyEntity = parseDepositMoneyMessage(message, thisPhone ?: "")

            // insert transaction to db
            depositMoneyEntity?.let {
                depositMoneyDao.insert(it)
                onSuccess()
            }?: run {
                // handle insert data into db errors
                return onFailure("Failed to parse deposit money message")
            }
        } catch (e: Exception){
            onFailure(e.message ?: "Unknown error")
        }
    }
}