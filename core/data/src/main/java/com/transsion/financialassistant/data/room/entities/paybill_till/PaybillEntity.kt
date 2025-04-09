package com.transsion.financialassistant.data.room.entities.paybill_till

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class PayBillEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val paidToName: String,
    val paidToAccountNo: String,
    val amount: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val date: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val time: String,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val transactionType: TransactionType = TransactionType.PAY_BILL
)
