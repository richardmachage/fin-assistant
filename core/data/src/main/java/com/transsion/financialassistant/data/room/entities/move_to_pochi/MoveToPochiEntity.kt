package com.transsion.financialassistant.data.room.entities.move_to_pochi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionType

@Entity
data class MoveToPochiEntity(
    @PrimaryKey
    val transactionCode: String,
    val amount: Double,
    val date: String,
    val time: String,
    val businessBalance: Double,
    val mpesaBalance: Double,
    val transactionCost: Double,
    val transactionType: TransactionType = TransactionType.MOVE_TO_POCHI
)
