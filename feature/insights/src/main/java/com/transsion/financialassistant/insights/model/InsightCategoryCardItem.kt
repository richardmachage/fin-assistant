package com.transsion.financialassistant.insights.model

import androidx.annotation.DrawableRes

data class InsightCategoryCardItem(
    val tittle: String,
    val amount: String,
    @DrawableRes val categoryIcon: Int,
)

