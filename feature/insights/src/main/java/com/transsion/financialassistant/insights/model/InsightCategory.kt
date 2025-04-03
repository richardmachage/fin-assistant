package com.transsion.financialassistant.insights.model

import androidx.annotation.StringRes
import com.transsion.financialassistant.insights.R

enum class InsightCategories(@StringRes val description: Int) {
    PERSONAL(R.string.personal_finances),
    BUSINESS(R.string.business_finances)
}