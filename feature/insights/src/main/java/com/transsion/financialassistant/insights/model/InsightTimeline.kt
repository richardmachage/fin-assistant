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

    fun getTimeline(): InsightTimelineRange {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE d MMM", Locale.getDefault())
        val formatter2 = DateTimeFormatter.ofPattern("d/M/yy", Locale.getDefault())
        val timeFormatter = DateTimeFormatter.ofPattern("h:mma", Locale.getDefault())

        val roomFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

        return when (this) {
            TODAY -> {
                val date = now.toLocalDate().format(formatter)
                InsightTimelineRange(
                    startDate = now.format(roomFormatter),
                    endDate = now.format(roomFormatter),
                    time = now.format(timeFormatter),
                    displayInfo = "Today $date, ${now.format(timeFormatter)}"
                )
            }

            WEEK -> {
                val currentDate = now.toLocalDate()
                val startOfWeek = currentDate.with(DayOfWeek.MONDAY)
                val endOfWeek = currentDate.with(DayOfWeek.SUNDAY)


                InsightTimelineRange(
                    startDate = startOfWeek.format(roomFormatter),
                    endDate = endOfWeek.format(roomFormatter),
                    time = now.format(timeFormatter),
                    displayInfo = "From ${startOfWeek.format(formatter)} - ${
                        endOfWeek.format(
                            formatter
                        )
                    }, ${now.format(timeFormatter)}"
                )
            }

            MONTH -> {
                val currentDate = now.toLocalDate()
                val startOfMonth = currentDate.withDayOfMonth(1)
                val endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth())

                InsightTimelineRange(
                    startDate = startOfMonth.format(roomFormatter),
                    endDate = endOfMonth.format(roomFormatter),
                    time = now.format(timeFormatter),
                    displayInfo = "From ${startOfMonth.format(formatter)} - ${
                        endOfMonth.format(
                            formatter
                        )
                    }, ${now.format(timeFormatter)}"
                )
            }

        }
    }

}