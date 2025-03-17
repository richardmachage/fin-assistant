package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType

interface TransactionRepo {
    fun getTransactionType(message: String): TransactionType
}
