package com.transsion.financialassistant.data.room.views.business

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = """
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, businessBalance, sentToName FROM SendFromPochiEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date,time,transactionType, businessBalance, 'OUT' as transactionCategory,'MY MPESA' as sentToName, transactionCost FROM MoveFromPochiEntity
    """
)
data class UnifiedOutGoingTransactionsBusiness(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val sentToName: String,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val businessBalance: Double,
    val transactionCost: Double
)
