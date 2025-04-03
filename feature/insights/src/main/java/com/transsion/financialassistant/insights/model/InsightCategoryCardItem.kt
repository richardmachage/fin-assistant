package com.transsion.financialassistant.insights.model

import androidx.compose.ui.graphics.painter.Painter

data class InsightCategoryCardItem(
    val tittle: String,
    val amount: String,
    val categoryIcon: Painter,
)
