package com.transsion.financialassistant.data.room.entities.send_pochi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SendPochiDao {
    // insert
    @Insert
    suspend fun insert(sendPochiEntity: SendPochiEntity)

    // update
    @Update
    suspend fun update(sendPochiEntity: SendPochiEntity)

    // delete
    @Delete
    suspend fun delete(sendPochiEntity: SendPochiEntity)

    // read
    @Query("SELECT * FROM SendPochiEntity")
    fun getAll(): Flow<List<SendPochiEntity>>
}