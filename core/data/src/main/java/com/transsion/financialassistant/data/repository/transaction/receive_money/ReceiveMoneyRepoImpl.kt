package com.transsion.financialassistant.data.repository.transaction.receive_money

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyDao
import javax.inject.Inject

class ReceiveMoneyRepoImpl @Inject constructor(
    private val receiveMoneyDao: ReceiveMoneyDao
) : ReceiveMoneyRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertReceiveMoneyTransaction(
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
            val receiveMoneyEntity = parseReceiveMoneyMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            receiveMoneyEntity?.let {
                receiveMoneyDao.insert(it)
                onSuccess()

            } ?: run {
                //Handle error state
                onFailure("Failed to parse receive money message")
            }
        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }


}