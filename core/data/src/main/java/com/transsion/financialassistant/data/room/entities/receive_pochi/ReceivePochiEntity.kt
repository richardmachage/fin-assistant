package com.transsion.financialassistant.data.room.entities.receive_pochi

import androidx.room.PrimaryKey

data class ReceivePochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val phone: String,
    val receiveFromName: String,
    val receiveFromPhone: String,
)
