package com.transsion.financialassistant.data.room.entities.bundles_purchase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class BundlesPurchaseEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val accountName: String,
    val accountNumber: String,
    val mpesaBalance: Double,
    val date: String,
    val startDate: String? = null,
    val endDate: String? = null,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT,
    val transactionType: TransactionType = TransactionType.BUNDLES_PURCHASE
)
