package com.transsion.financialassistant.data.room.entities.receive_money

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class ReceiveMoneyEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val receiveFromName: String,
    val receiveFromPhone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory = TransactionCategory.IN

)
