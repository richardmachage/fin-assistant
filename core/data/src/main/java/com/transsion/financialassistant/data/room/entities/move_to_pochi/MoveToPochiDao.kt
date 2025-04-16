package com.transsion.financialassistant.data.room.entities.move_to_pochi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoveToPochiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moveToPochiEntity: MoveToPochiEntity)

    @Query("SELECT * FROM MoveToPochiEntity")
    fun getAll(): Flow<List<MoveToPochiEntity>>

    @Query("SELECT * FROM MoveToPochiEntity WHERE transactionCode = :transactionCode")
    fun getByTransactionCode(transactionCode: String): MoveToPochiEntity?

    @Update
    suspend fun updateRecord(moveToPochiEntity: MoveToPochiEntity)

    @Delete
    suspend fun deleteRecord(moveToPochiEntity: MoveToPochiEntity)

}