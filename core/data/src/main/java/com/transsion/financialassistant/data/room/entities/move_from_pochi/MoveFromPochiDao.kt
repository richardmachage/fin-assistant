package com.transsion.financialassistant.data.room.entities.move_from_pochi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MoveFromPochiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moveFromPochiEntity: MoveFromPochiEntity)

    @Query("SELECT * FROM MoveFromPochiEntity")
    fun getAll(): Flow<List<MoveFromPochiEntity>>

    @Query("SELECT * FROM MoveFromPochiEntity WHERE transactionCode = :transactionCode")
    fun getByTransactionCode(transactionCode: String): MoveFromPochiEntity?

    @Update
    suspend fun updateRecord(moveFromPochiEntity: MoveFromPochiEntity)

    @Delete
    suspend fun deleteRecord(moveFromPochiEntity: MoveFromPochiEntity)

    @Query("SELECT * FROM MoveFromPochiEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getRecordsByDate(startDate: String, endDate: String): List<MoveFromPochiEntity>

}