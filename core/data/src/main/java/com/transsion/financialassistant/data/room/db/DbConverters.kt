package com.transsion.financialassistant.data.room.db

import androidx.room.TypeConverter
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class FinancialAssistantDbConverters {
    @TypeConverter
    fun fromDate(date: java.sql.Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): java.sql.Date? {
        return timestamp?.let { it ->
            java.sql.Date(it)
        }
    }

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String = type.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType =
        TransactionType.entries.firstOrNull { it.name == value } ?: TransactionType.UNKNOWN

    @TypeConverter
    fun fromTransactionCategory(type: TransactionCategory): String = type.name

    @TypeConverter
    fun toTransactionCategory(value: String): TransactionCategory =
        TransactionCategory.valueOf(value)


    private val appFormatter =
        DateTimeFormatter.ofPattern("d/M/yy", Locale.getDefault()) // e.g. 1/3/25
    private val dbFormatter = DateTimeFormatter.ISO_LOCAL_DATE // e.g. 2025-03-01

    @TypeConverter
    fun fromAppFormatToDb(dateString: String?): String? {
        return dateString?.let {
            try {
                val parsedDate = LocalDate.parse(it, appFormatter)
                parsedDate.format(dbFormatter)
            } catch (e: Exception) {
                null // or just return it if you're not sure
            }
        }
    }

    @TypeConverter
    fun fromDbToAppFormat(dateString: String?): String? {
        return dateString?.let {
            try {
                val parsedDate = LocalDate.parse(it, dbFormatter)
                parsedDate.format(appFormatter)
            } catch (e: Exception) {
                null
            }
        }
    }

}