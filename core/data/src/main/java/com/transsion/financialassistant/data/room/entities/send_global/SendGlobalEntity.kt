package com.transsion.financialassistant.data.room.entities.send_global

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class SendGlobalEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val sentTo: String,
    val amount: String,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    // transactionType: TransactionType  //FIXME
)
