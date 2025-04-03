package com.transsion.financialassistant.insights.model

import androidx.annotation.StringRes
import com.transsion.financialassistant.insights.R
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class InsightTimeline(@StringRes val description: Int) {
    TODAY(R.string.today),
    WEEK(R.string.this_week),
    MONTH(R.string.this_month);

    fun getTimeline(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())
        val timeFormatter = DateTimeFormatter.ofPattern("h:mma", Locale.getDefault())

        return when (this) {
            TODAY -> {
                val date = now.toLocalDate().format(formatter)
                "Today $date, ${now.format(timeFormatter)}"
            }

            WEEK -> {
                val currentDate = now.toLocalDate()
                val startOfWeek = currentDate.with(DayOfWeek.SUNDAY)
                val endOfWeek = currentDate.with(DayOfWeek.SATURDAY)
                "From ${startOfWeek.format(formatter)} - ${endOfWeek.format(formatter)}, ${
                    now.format(
                        timeFormatter
                    )
                }"
            }

            MONTH -> {
                val currentDate = now.toLocalDate()
                val startOfMonth = currentDate.withDayOfMonth(1)
                val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())
                "From ${startOfMonth.format(formatter)} - ${endOfMonth.format(formatter)}, ${
                    now.format(
                        timeFormatter
                    )
                }"
            }

        }
    }

}