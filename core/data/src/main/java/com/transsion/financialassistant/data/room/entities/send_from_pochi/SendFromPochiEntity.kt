package com.transsion.financialassistant.data.room.entities.send_from_pochi

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SendFromPochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val amount: Double,
    val date: String,
    val time: String,
    val phone: String,
    val businessBalance: Double,
    val sentToName: String,
    val transactionCost: Double
)
