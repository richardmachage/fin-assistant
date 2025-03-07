package com.transsion.financialassistant.data.room.entities.paybill_till

import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

data class PayBillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val transactionCode: String,
    val phone: String,
    val paidToName: String,
    val paidToAccountNo: String,
    val amount: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val transactionCost: Double
)
