package com.transsion.financialassistant.data.room.entities.receive_mshwari

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveMshwariDao {
    // create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(receiveMshwari: ReceiveMshwariEntity)

    // update
    @Update
    suspend fun update(receiveMshwari: ReceiveMshwariEntity)

    // delete an item
    @Delete
    suspend fun delete(receiveMshwari: ReceiveMshwariEntity)

    // read
    @Query("SELECT * FROM ReceiveMshwariEntity")
    fun getAll(): Flow<List<ReceiveMshwariEntity>>

    // delete all
    @Query("DELETE FROM ReceiveMshwariEntity")
    suspend fun deleteAll()

    // select an item by transactionCode
    @Query("SELECT * FROM ReceiveMshwariEntity WHERE transactionCode = :transactionCode")
    suspend fun getByTransactionCode(transactionCode: String): ReceiveMshwariEntity?

    @Query("SELECT * FROM ReceiveMshwariEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceiveMshwariTransactionsByDate(startDate: String, endDate: String): List<ReceiveMshwariEntity>

}