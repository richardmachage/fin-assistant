package com.transsion.financialassistant.data.room.entities.send_global

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SendGlobalDao {
    // insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sendGlobalEntity: SendGlobalEntity)

    // update
    @Update
    suspend fun update(sendGlobalEntity: SendGlobalEntity)

    // delete
    @Delete
    suspend fun delete(sendGlobalEntity: SendGlobalEntity)

    // read
    @Query("SELECT * FROM SendGlobalEntity")
    fun getAll(): Flow<List<SendGlobalEntity>>

    @Query("SELECT * FROM SendGlobalEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getSendGlobalTransactionsByDate(startDate: String, endDate: String): List<SendGlobalEntity>

}