package com.transsion.financialassistant.data.room.entities.send_from_pochi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType


@Entity
data class SendFromPochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val amount: Double,
    val date: String,
    val time: String,
    val phone: String,
    val businessBalance: Double,
    val sentToName: String,
    val transactionCost: Double,
    val transactionType: TransactionType = TransactionType.SEND_MONEY_FROM_POCHI,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
