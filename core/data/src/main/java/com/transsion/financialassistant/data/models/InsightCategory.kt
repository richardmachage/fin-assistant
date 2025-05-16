package com.transsion.financialassistant.data.models

import androidx.annotation.StringRes
import com.transsion.financialassistant.data.R

enum class InsightCategory(@StringRes val description: Int) {
    PERSONAL(R.string.personal_finances),
    BUSINESS(R.string.business_finances)
}