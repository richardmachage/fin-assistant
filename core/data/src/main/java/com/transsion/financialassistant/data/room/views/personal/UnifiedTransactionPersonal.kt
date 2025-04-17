package com.transsion.financialassistant.data.room.views.personal

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = """
        SELECT transactionCode,phone, amount, date, time,name, transactionCategory,mpesaBalance, transactionType, NULL AS transactionCost FROM UnifiedIncomingTransaction
        UNION ALL
        SELECT transactionCode,phone, amount, date, time,name, transactionCategory, mpesaBalance,transactionType, transactionCost FROM UnifiedOutGoingTransaction
    """
)
data class UnifiedTransactionPersonal(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String?,
    val transactionCost: Double?,
    val mpesaBalance: Double,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)




