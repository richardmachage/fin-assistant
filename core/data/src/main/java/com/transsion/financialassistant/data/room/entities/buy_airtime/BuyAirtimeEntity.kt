package com.transsion.financialassistant.data.room.entities.buy_airtime

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class BuyAirtimeEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val transactionType: TransactionType = TransactionType.AIRTIME_PURCHASE

)
