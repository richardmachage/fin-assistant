package com.transsion.financialassistant.data.room.entities.send_mshwari

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SendMshwariDao {
    // create
    @Insert
    suspend fun insert(sendMshwariEntity: SendMshwariEntity)

    // update
    @Update
    suspend fun update(sendMshwariEntity: SendMshwariEntity)

    // delete
    @Delete
    suspend fun delete(sendMshwariEntity: SendMshwariEntity)

    // read
    @Query("SELECT * FROM send_mshwari")
    fun getAll(): Flow<List<SendMshwariEntity>>

}