package com.transsion.financialassistant.data.room.entities.send_mshwari

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class SendMshwariEntity(
    @PrimaryKey
    val transactionCode: String,
    val amount: Double,
    val phone: String,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val mshwariBalance: Double,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val transactionType: TransactionType = TransactionType.SEND_MSHWARI
)
