package com.transsion.financialassistant.data.room.entities.receive_mshwari

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class ReceiveMshwariEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val account: String,
    val amount: Double,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val mshwariBalance: Double,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.IN,
    val transactionType: TransactionType = TransactionType.RECEIVE_MSHWARI
)
