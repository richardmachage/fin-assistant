package com.transsion.financialassistant.home.screens.all_transactions.model

import androidx.annotation.StringRes
import com.transsion.financialassistant.data.R

enum class AllTransactionsCategory (@StringRes val description: Int) {
    PERSONAL(R.string.personal_finances),
    BUSINESS(R.string.business_finances)
}