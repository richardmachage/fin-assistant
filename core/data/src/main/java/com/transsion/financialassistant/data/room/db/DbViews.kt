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
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM BundlesPurchaseEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM BuyAirtimeEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM BuyGoodsEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM PayBillEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM SendMoneyEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM SendPochiEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory FROM SendMshwariEntity

    """
)
data class UnifiedOutGoingTransaction(
    val transactionCode: String,
    val phone: String,
    val amount: Double,
    val date: String,
    val time: String,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)

