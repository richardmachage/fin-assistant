package com.transsion.financialassistant.data.repository.transaction.buy_airtime

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeDao
import javax.inject.Inject

class BuyAirtimeRepoImpl @Inject constructor(
    private val buyAirtimeDao: BuyAirtimeDao
): BuyAirtimeRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertBuyAirtimeTransaction(
        message: String,
        context: Context,
        subId: Int,
        phone: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        var thisPhone = phone

        try {
            //get receiving address
            if (thisPhone == null) {
                thisPhone = getReceivingAddress(context, subId)
            }
            // Parse the message to get the transaction details
            val buyAirtimeEntity = parsePurchaseAirtimeMessage(message, thisPhone?: "")

            //insert transaction into the database
            buyAirtimeEntity?.let {
                buyAirtimeDao.insert(it)
                onSuccess()
            } ?: run {
                // handle insert data into db errors
                onFailure("Failed to parse buy airtime message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error")
        }
    }


}