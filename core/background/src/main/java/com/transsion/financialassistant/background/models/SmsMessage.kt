package com.transsion.financialassistant.background.models

data class SmsMessage(
    val id: Long,
    val address: String,
    val body: String,
    val date: Long,
    val type: Int // 1: inbox, 2: sent
)