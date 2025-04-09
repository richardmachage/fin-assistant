package com.transsion.financialassistant.presentation.components.graphs.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class CategoryDistribution(
    val name: String,
    val percentage: Float,
    val color: Color,
    val amount: Float,
    @DrawableRes val icon: Int? = null
)
