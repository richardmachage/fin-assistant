package com.transsion.financialassistant.data.room.entities.buygoods_till

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class BuyGoodsEntity(
    @PrimaryKey
    val id: Int = 0,
    val transactionCode: String,
    val phone: String,
    val paidTo: String,
    val amount: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val transactionType: TransactionType = TransactionType.BUY_GOODS

)
