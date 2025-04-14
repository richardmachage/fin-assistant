package com.transsion.financialassistant.data.utils

import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val monthDayFormatter = DateTimeFormatter.ofPattern("MMM d")

private val appFormatter =
    DateTimeFormatter.ofPattern("d/M/yy") // e.g. 1/3/25
private val dbFormatter =
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
            this
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