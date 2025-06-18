package com.transsion.financialassistant.data.room.entities.send_till

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SendTillEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val sentToName: String,
    val amount: String,
    val date: String,
    val time: String,
    val businessBalance: String,
)
