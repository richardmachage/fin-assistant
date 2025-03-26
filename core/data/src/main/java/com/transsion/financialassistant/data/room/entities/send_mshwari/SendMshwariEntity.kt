package com.transsion.financialassistant.data.room.entities.send_mshwari

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.transsion.financialassistant.data.models.TransactionCategory

@Entity(tableName = "send_mshwari")
data class SendMshwariEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionCode: String,
    val amount: Double,
    val phone: String,
    //val account: String,
    val date: String,
    val time: String,
    val mpesaBalance: Double,
    val mshwariBalance: Double,
    val transactionCost: Double,
    val transactionCategory: TransactionCategory = TransactionCategory.OUT
)
