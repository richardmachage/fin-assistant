package com.transsion.financialassistant.data.room.views.business

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType


@DatabaseView(
    value = """
        SELECT transactionCode, phone, amount, date, time, transactionType, businessBalance, transactionCategory, sentToName as name, 0.0 as transactionCost  from SendTillEntity
        UNION ALL 
        SELECT transactionCode, phone, amount, date, time, transactionType,  businessBalance,transactionCategory, receiveFromName as name, transactionCost from ReceiveTillEntity
    """
)
data class UnifiedTillTransactions(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val businessBalance: Double,
    val transactionCost: Double
)
