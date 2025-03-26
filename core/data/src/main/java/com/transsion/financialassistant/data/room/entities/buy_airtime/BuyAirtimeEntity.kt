package com.transsion.financialassistant.data.room.entities.buy_airtime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity(tableName = "buy_airtime")
data class BuyAirtimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
