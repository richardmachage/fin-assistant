package com.transsion.financialassistant.data.room.entities.send_from_pochi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SendFromPochiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sendFromPochiEntity: SendFromPochiEntity)

    @Query("SELECT * FROM SendFromPochiEntity")
    fun getAll(): Flow<List<SendFromPochiEntity>>

    @Query("SELECT * FROM SendFromPochiEntity WHERE transactionCode = :transactionCode")
    fun getByTransactionCode(transactionCode: String): SendFromPochiEntity?

    @Update
    suspend fun updateRecord(sendFromPochiEntity: SendFromPochiEntity)

    @Delete
    suspend fun deleteRecord(sendFromPochiEntity: SendFromPochiEntity)

    @Query("SELECT * FROM SendFromPochiEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getRecordsByDate(startDate: String, endDate: String): List<SendFromPochiEntity>

}