package com.transsion.financialassistant.admin.model


data class FeedBack(
    val title: String = "",
    val description: String = "",
    val attachment: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)