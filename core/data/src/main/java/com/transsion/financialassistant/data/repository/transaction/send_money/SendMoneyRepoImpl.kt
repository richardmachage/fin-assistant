package com.transsion.financialassistant.data.repository.transaction.send_money

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyDao
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import kotlinx.coroutines.flow.Flow
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
            val sendMoneyEntity = parseSendMoneyMessage(message, thisPhone ?: "")

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

    override suspend fun getAllSendMoneyTransactions(): Flow<List<SendMoneyEntity>> {
        return sendMoneyDao.getAll()
    }

}