package com.transsion.financialassistant.insights.model

data class InsightTimelineRange(
//    val currentDate : String,
    val startDate: String? = null,
    val endDate: String? = null,
    val time: String,
    val displayInfo: String
)
