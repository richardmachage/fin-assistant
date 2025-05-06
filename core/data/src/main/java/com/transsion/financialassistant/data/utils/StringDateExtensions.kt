package com.transsion.financialassistant.data.utils

import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private val monthDayFormatter = DateTimeFormatter.ofPattern("MMM d")

val appFormatter =
    DateTimeFormatter.ofPattern("d/M/yy") // e.g. 1/3/25
val appFormatter2 = DateTimeFormatter.ofPattern("d/M/yyyy") //e.g 1/3/2025

val dbFormatter =
    DateTimeFormatter.ofPattern("yyyy/MM/dd")//DateTimeFormatter.ISO_LOCAL_DATE // e.g. 2025-03-01
private val dbTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
private val appTimeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

fun String.toDbTime(): String {
    return this.let {
        try {
            val parsedDate = LocalTime.parse(it, appTimeFormatter)
            parsedDate.format(dbTimeFormatter)
        } catch (e: Exception) {
            println("error, ${e.message}")

            this
        }
    }
}

fun String.toAppTime(): String {
    return this.let {
        try {
            val parsedDate = LocalTime.parse(it, dbTimeFormatter)
            parsedDate.format(appTimeFormatter)
        } catch (e: Exception) {
            println("error, ${e.message}")
            this
        }
    }
}

fun String.toDbDate(): String {
    return this.let {
        try {
            val parsedDate = LocalDate.parse(it, appFormatter)
            parsedDate.format(dbFormatter)
        } catch (e: Exception) {
            try {
                val parsedDate = LocalDate.parse(it, appFormatter2)
                parsedDate.format(dbFormatter)
            } catch (e: Exception) {
                println("error, ${e.message}")
                this
            }
        }
    }
}

fun String.toMpesaDate(): String {
    return this.let {
        try {
            val parsedDate = LocalDate.parse(it, dbFormatter)
            parsedDate.format(appFormatter)
        } catch (e: Exception) {
            this
        }
    }
}

fun String.toMonthDayDate(): String {
    return try {
        val parsedDate = LocalDate.parse(this, dbFormatter)
        parsedDate.format(monthDayFormatter)
    } catch (e: Exception) {
        this
    }
}

fun String.formatAsCurrency(locale: Locale = Locale.US, maxFractionDigits: Int = 2): String {
    return try {
        val number = this.replace(",", "").replace("\\", "").toDouble()
        val formatter = NumberFormat.getNumberInstance(locale).apply {
            isGroupingUsed = true
            maximumFractionDigits = maxFractionDigits
            minimumFractionDigits = 0
        }
        formatter.format(number)
    } catch (e: Exception) {
        this
    }
}


fun LocalDate.getWeekRange(startOfWeek: DayOfWeek = DayOfWeek.MONDAY): Pair<String, String> {
    val currentDayOfWeek = this.dayOfWeek
    val daysFromStart = (7 + currentDayOfWeek.value - startOfWeek.value) % 7
    val startDate = this.minusDays(daysFromStart.toLong())
    val endDate = startDate.plusDays(6)
    return startDate.format(dbFormatter) to endDate.format(dbFormatter)
}

fun LocalDate.getLastWeekRange(startOfWeek: DayOfWeek = DayOfWeek.MONDAY): Pair<String, String> {
    val currentDayOfWeek = this.dayOfWeek
    val daysFromStart = (7 + currentDayOfWeek.value - startOfWeek.value) % 7
    val thisWeekStartDate = this.minusDays(daysFromStart.toLong())
    val lastWeekStartDate = thisWeekStartDate.minusWeeks(1)
    val lastWeekEndDate = lastWeekStartDate.plusDays(6)


    return lastWeekStartDate.format(dbFormatter) to lastWeekEndDate.format(dbFormatter)
}


fun LocalDate.getMonthRange(): Pair<String, String> {
    val yearMonth = YearMonth.from(this)
    val start = yearMonth.atDay(1)
    val end = yearMonth.atEndOfMonth()
    return start.format(dbFormatter) to end.format(dbFormatter)
}

fun LocalDate.getLastMonthRange(): Pair<String, String> {
    val lastMonth = YearMonth.from(this).minusMonths(1)
    val lastMonthStart = lastMonth.atDay(1)
    val lastMonthEnd = lastMonth.atEndOfMonth()
    return lastMonthStart.format(dbFormatter) to lastMonthEnd.format(dbFormatter)

}