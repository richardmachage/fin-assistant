package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity

interface TransactionRepo {
    fun getTransactionType(message: String): TransactionType

    fun parseSendMoneyMessage(message: String, phone: String): SendMoneyEntity?
}
