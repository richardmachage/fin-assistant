package com.transsion.financialassistant.data.models

data class DailyTransactionTotal(
    val date: String,
    val time: String,
    val totalAmount: Double
)
