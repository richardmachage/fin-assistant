package com.transsion.financialassistant.data.room.entities.receive_mshwari

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveMshwariDao {
    @Insert
    suspend fun insert(receiveMshwari: ReceiveMshwariEntity)

    @Query("SELECT * FROM ReceiveMshwariEntity")
    fun getAll(): Flow<List<ReceiveMshwariEntity>>

    @Query("DELETE FROM ReceiveMshwariEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ReceiveMshwariEntity WHERE transactionCode = :transactionCode")
    suspend fun getByTransactionCode(transactionCode: String): ReceiveMshwariEntity?


}