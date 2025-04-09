package com.transsion.financialassistant.data.room.db

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = """
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM ReceiveMoneyEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM ReceiveMshwariEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM ReceivePochiEntity
        UNION ALL
        SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM DepositMoneyEntity
    """
)
data class UnifiedIncomingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

@DatabaseView(
    value = """
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM BundlesPurchaseEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,transactionCost FROM BuyAirtimeEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM BuyGoodsEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM PayBillEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM SendMoneyEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM SendPochiEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost FROM SendMshwariEntity

    """
)
data class UnifiedOutGoingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

