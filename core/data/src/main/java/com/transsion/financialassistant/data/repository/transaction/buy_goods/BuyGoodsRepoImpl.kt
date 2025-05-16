package com.transsion.financialassistant.data.repository.transaction.buy_goods

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsDao
import javax.inject.Inject

class BuyGoodsRepoImpl @Inject constructor(
    private val buyGoodsDao: BuyGoodsDao
) : BuyGoodsRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertBuyGoodsTransaction(
        message: String,
        context: Context,
        phone: String?,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        var thisPhone = phone
        try {

            //get the receiving address
            if (thisPhone == null) {
                thisPhone = getReceivingAddress(context, subId)
            }

            //parse the message
            val buyGoodsEntity = parseBuyGoodsMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            buyGoodsEntity?.let {
                buyGoodsDao.insert(it)
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