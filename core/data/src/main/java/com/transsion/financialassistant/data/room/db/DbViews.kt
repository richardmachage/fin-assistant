package com.transsion.financialassistant.data.room.db

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = """
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, receiveFromName as name FROM ReceiveMoneyEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, NULL AS name  FROM ReceiveMshwariEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, NULL AS name FROM ReceivePochiEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, agentDepositedTo as Name FROM DepositMoneyEntity
    """
)
data class UnifiedIncomingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String?,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

@DatabaseView(
    value = """
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, NULL AS name FROM BundlesPurchaseEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,transactionCost, NULL AS name FROM BuyAirtimeEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, paidTo as name FROM BuyGoodsEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, paidToName as name FROM PayBillEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, sentToName as name FROM SendMoneyEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, sentToName as name FROM SendPochiEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, NULL as name FROM SendMshwariEntity

    """
)
data class UnifiedOutGoingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String?,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)


@DatabaseView(
    value = """
        SELECT transactionCode,phone, amount, date, time,name, transactionCategory, transactionType, NULL AS transactionCost FROM UnifiedIncomingTransaction
        UNION ALL
        SELECT transactionCode,phone, amount, date, time,name, transactionCategory, transactionType, transactionCost FROM UnifiedOutGoingTransaction
    """
)
data class UnifiedTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val name: String?,
    val transactionCost: Double?,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

