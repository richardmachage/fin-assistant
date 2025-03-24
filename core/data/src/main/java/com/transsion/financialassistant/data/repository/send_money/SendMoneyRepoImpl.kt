package com.transsion.financialassistant.data.repository.send_money

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyDao
import javax.inject.Inject

class SendMoneyRepoImpl @Inject constructor(
    private val sendMoneyDao: SendMoneyDao,
) : SendMoneyRepo, TransactionRepoImpl() {

    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertSendMoneyTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {


            //get the receiving address
            val phone = getReceivingAddress(context, subId)

            //parse the message
            val sendMoneyEntity = parseSendMoneyMessage(message, phone ?: "")

            //insert the transaction into the database
            sendMoneyEntity?.let {
                sendMoneyDao.insert(it)
                onSuccess()

            } ?: run {
                //Handle error state
                onFailure("Failed to parse send money message")
            }
        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }

}