package com.transsion.financialassistant.data.repository.transaction.bundles_purchase

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseDao
import javax.inject.Inject

class BundlesPurchaseRepoImpl @Inject constructor(
    private val bundlesPurchaseDao: BundlesPurchaseDao
): BundlesPurchaseRepo, TransactionRepoImpl(){
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertBundlesPurchaseTransaction(
        message: String,
        context: Context,
        subId: Int,
        phone: String?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        var thisPhone = phone

        try {
            // get the receiving address
            if (thisPhone == null){
                thisPhone = getReceivingAddress(context, subId)
            }

            // parse the message to get transaction details
            val bundlesPurchaseEntity = parseBundlesPurchaseMessage(message, thisPhone ?: "")

            // insert the transaction to db
            bundlesPurchaseEntity?.let {
                bundlesPurchaseDao.insert(it)
                onSuccess()
            } ?: run {
                onFailure("Failed to parse bundles purchase message")
            }

        } catch (e: Exception){
            onFailure(e.message ?: "Unknown Error")
        }
    }

}