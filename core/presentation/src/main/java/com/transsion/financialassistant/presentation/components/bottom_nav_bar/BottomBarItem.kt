package com.transsion.financialassistant.presentation.components.bottom_nav_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomBarItem(
    val route: Any,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
)
