package com.transsion.financialassistant.data.room.views.business

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType


@DatabaseView(
    value = """
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, businessBalance, receiveFromName , "0.0" as transactionCost FROM ReceivePochiEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date,time, transactionType, "IN" as transactionCategory, businessBalance, "MY MPESA" as receiveFromName, transactionCost FROM MoveToPochiEntity
    """
)
data class UnifiedIncomingTransactionsBusiness(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val receiveFromName: String,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val businessBalance: Double,
    val transactionCost: Double
)
