package com.transsion.financialassistant.data.repository.transaction.paybill

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillDao
import javax.inject.Inject

class PayBillRepoImpl @Inject constructor(
    private val payBillDao: PayBillDao
) : PayBillRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertPayBillTransaction(
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
            val payBillEntity = parsePayBillMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            payBillEntity?.let {
                payBillDao.insert(it)
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