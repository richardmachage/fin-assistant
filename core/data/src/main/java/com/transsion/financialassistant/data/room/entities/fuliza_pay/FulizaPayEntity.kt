package com.transsion.financialassistant.data.room.entities.fuliza_pay

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity
data class FulizaPayEntity(
    @PrimaryKey
    val transactionCode: String,
    val amount: Double,
    val availableFulizaLimit: Double,
    val mpesaBalance: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val date: String,
    val time: String,
    val transactionCost: Double,
    //val transactionType: TransactionType = TransactionType.PAY_BILL

)
