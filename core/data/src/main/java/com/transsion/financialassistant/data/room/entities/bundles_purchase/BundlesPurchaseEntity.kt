package com.transsion.financialassistant.data.room.entities.bundles_purchase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity(tableName = "bundles_purchase")
data class BundlesPurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val accountName: String,
    val accountNumber: String,
    val mpesaBalance: Double,
    val date: String,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
