package com.transsion.financialassistant.data.room.views.personal

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = "SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,mpesaBalance, receiveFromName AS name FROM ReceiveMoneyEntity UNION ALL SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,mpesaBalance, NULL AS name  FROM ReceiveMshwariEntity UNION ALL SELECT transactionCode, phone, amount, date, time, transactionType, 'IN' AS transactionCategory,mpesaBalance, 'MY POCHI' AS name FROM MoveFromPochiEntity UNION ALL SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, mpesaBalance, agentDepositedTo AS name FROM DepositMoneyEntity UNION ALL SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, mpesaBalance, 'Reversal' AS name  FROM ReversalCreditEntity"
)
data class UnifiedIncomingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String?,
    val mpesaBalance: Double,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

