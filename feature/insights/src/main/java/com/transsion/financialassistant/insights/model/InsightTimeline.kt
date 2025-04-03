package com.transsion.financialassistant.insights.model

import androidx.annotation.StringRes
import com.transsion.financialassistant.insights.R

enum class InsightTimeline(@StringRes val description: Int) {
    TODAY(R.string.today),
    WEEK(R.string.this_week),
    MONTH(R.string.this_month),

}