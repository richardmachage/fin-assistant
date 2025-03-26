package com.transsion.financialassistant.data.room.entities.buy_airtime

import com.transsion.financialassistant.data.models.TransactionCategory

data class BuyAirtimeEntity(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
