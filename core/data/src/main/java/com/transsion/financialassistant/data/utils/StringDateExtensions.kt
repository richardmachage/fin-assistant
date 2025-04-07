package com.transsion.financialassistant.data.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val monthDayFormatter = DateTimeFormatter.ofPattern("MMM d")

private val appFormatter =
    DateTimeFormatter.ofPattern("d/M/yy") // e.g. 1/3/25
private val dbFormatter =
    DateTimeFormatter.ofPattern("yyyy/MM/dd")//DateTimeFormatter.ISO_LOCAL_DATE // e.g. 2025-03-01

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