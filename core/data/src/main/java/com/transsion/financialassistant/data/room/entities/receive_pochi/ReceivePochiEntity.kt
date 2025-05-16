package com.transsion.financialassistant.data.room.entities.receive_pochi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class ReceivePochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val receiveFromName: String,
    val amount: Double,
    val date: String,
    val time: String,
    val businessBalance: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val transactionType: TransactionType = TransactionType.RECEIVE_POCHI
)
