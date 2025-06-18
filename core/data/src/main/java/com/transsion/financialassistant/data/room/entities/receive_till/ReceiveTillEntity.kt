package com.transsion.financialassistant.data.room.entities.receive_till

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReceiveTillEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val receiveFromName: String,
    val receiveFromNumber: String,
    val amount: Double,
    val date: String,
    val time: String,
    val businessBalance: Double,
    val transactionCost: Double,
)
