package com.transsion.financialassistant.data.room.entities.search_history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val searchQuery: String,
    val timestamp: Long = System.currentTimeMillis()
)