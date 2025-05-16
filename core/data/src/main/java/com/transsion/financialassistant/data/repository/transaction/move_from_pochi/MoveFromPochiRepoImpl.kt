package com.transsion.financialassistant.data.repository.transaction.move_from_pochi

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.transsion.financialassistant.data.repository.getReceivingAddress
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.room.entities.move_from_pochi.MoveFromPochiDao
import javax.inject.Inject

class MoveFromPochiRepoImpl @Inject constructor(
    private val moveFromPochiDao: MoveFromPochiDao
) : MoveFromPochiRepo, TransactionRepoImpl() {
    @RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
    override suspend fun insertMoveFromPochiTransaction(
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
            val moveFromPochiEntity = parseMoveFromPochiMessage(message, thisPhone ?: "")

            //insert the transaction into the database
            moveFromPochiEntity?.let {
                moveFromPochiDao.insert(it)
            } ?: run {
                //Handle error state
                onFailure("Failed to parse move From Pochi message")
            }

        } catch (e: Exception) {
            onFailure(e.message ?: "Unknown error when inserting move From Pochi transaction")
        }
    }
}