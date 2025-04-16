package com.transsion.financialassistant.data.room.views.business

import androidx.room.DatabaseView
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType


@DatabaseView(

    //FIXME make a UNION of all the Business views here
    value = """
        SELECT transactionCode, phone, amount,date,time, receiveFromName as name, "IN" AS transactionCategory, transactionType, businessBalance AS mpesaBalance,  transactionCost FROM UnifiedIncomingTransactionsBusiness
    """
)
data class UnifiedTransactionBusiness(
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
