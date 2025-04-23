package com.transsion.financialassistant.data.room.views.personal

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

@DatabaseView(
    value = """
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, NULL AS name FROM BundlesPurchaseEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,transactionCost, mpesaBalance,NULL AS name FROM BuyAirtimeEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, paidTo as name FROM BuyGoodsEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,paidToName as name FROM PayBillEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,sentToName as name FROM SendMoneyEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, sentToName as name FROM SendPochiEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,NULL as name FROM SendMshwariEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, "OUT" AS transactionCategory, transactionCost, mpesaBalance, "MY POCHI" as name FROM MoveToPochiEntity 

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
    val mpesaBalance: Double,
    val transactionCategory: TransactionCategory,
    val transactionType: TransactionType
)
