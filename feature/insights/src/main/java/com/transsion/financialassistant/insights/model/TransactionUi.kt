package com.transsion.financialassistant.insights.model

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

data class TransactionUi(
    val title: String,
    val type: TransactionType,
    val inOrOut: TransactionCategory,
    val amount: String,
    val dateAndTime: String
)


