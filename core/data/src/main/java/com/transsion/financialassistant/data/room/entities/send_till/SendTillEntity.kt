package com.transsion.financialassistant.data.room.entities.send_till

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class SendTillEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val sentToName: String,
    val amount: Double,
    val date: String,
    val time: String,
    val businessBalance: Double,
    val transactionType: TransactionType = TransactionType.SEND_FROM_TILL,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
