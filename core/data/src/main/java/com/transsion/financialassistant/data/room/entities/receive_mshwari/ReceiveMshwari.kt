package com.transsion.financialassistant.data.room.entities.receive_mshwari

import com.transsion.financialassistant.data.models.TransactionCategory
import java.sql.Date

data class ReceiveMshwari(
    val transactionCode: String,
    val phone: String,
    val account: String,
    val amount: Double,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val mshwariBalance: Double,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.IN
)
