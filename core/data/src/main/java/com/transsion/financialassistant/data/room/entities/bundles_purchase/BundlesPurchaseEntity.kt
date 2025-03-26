package com.transsion.financialassistant.data.room.entities.bundles_purchase

import com.transsion.financialassistant.data.models.TransactionCategory

data class BundlesPurchaseEntity(
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
