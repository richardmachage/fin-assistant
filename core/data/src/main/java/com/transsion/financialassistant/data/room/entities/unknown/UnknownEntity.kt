package com.transsion.financialassistant.data.room.entities.unknown

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UnknownEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val timeStamp: Long = System.currentTimeMillis()
)
