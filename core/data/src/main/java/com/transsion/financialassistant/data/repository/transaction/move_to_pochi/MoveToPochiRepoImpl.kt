package com.transsion.financialassistant.data.repository.transaction.move_to_pochi

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.move_to_pochi.MoveToPochiDao
import javax.inject.Inject

class MoveToPochiRepoImpl @Inject constructor(
    private val moveToPochiDao: MoveToPochiDao
) : MoveToPochiRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertMoveToPochiTransaction(
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
            val moveToPochiEntity = parseMoveToPochiMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            moveToPochiEntity?.let {
                moveToPochiDao.insert(it)
            } ?: run {
                //Handle error state
                onFailure("Failed to parse move To Pochi message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error when inserting move to Pochi transaction")
        }
    }

}