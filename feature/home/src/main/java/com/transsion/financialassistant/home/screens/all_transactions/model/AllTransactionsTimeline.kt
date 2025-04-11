package com.transsion.financialassistant.home.screens.all_transactions.model

import androidx.annotation.StringRes
import com.transsion.financialassistant.home.R

enum class AllTransactionsTimeline(@StringRes val description: Int) {
    TODAY(R.string.today),
    YESTERDAY(R.string.yesterday),
}