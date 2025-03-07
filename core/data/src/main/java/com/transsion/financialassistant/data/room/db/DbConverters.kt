package com.transsion.financialassistant.data.room.db

import androidx.room.TypeConverter

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
}