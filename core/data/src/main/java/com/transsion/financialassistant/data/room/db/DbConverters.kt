package com.transsion.financialassistant.data.room.db

import androidx.room.TypeConverter
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType

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

}