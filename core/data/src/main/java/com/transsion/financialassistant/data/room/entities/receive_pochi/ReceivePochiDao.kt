package com.transsion.financialassistant.data.room.entities.receive_pochi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceivePochiDao {
    // create
    @Insert
    suspend fun insert(receivePochiEntity: ReceivePochiEntity)

    // delete
    @Delete
    suspend fun delete(receivePochiEntity: ReceivePochiEntity)

    // update
    @Update
    suspend fun update(receivePochiEntity: ReceivePochiEntity)

    // read
    @Query("SELECT * FROM ReceivePochiEntity")
    fun getAll(): Flow<List<ReceivePochiEntity>>

    @Query("SELECT * FROM ReceivePochiEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceivePochiTransactionsByDate(startDate: String, endDate: String): List<ReceivePochiEntity>

}