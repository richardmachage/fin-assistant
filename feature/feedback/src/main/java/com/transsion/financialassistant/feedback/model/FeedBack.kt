package com.transsion.financialassistant.feedback.model

data class FeedBack(
    val title: String,
    val description: String,
    val attachment: String? = null
)
