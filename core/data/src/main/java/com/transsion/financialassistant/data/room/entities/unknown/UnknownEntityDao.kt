package com.transsion.financialassistant.data.room.entities.unknown

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UnknownEntityDao {
    @Insert
    suspend fun insert(unknownEntity: UnknownEntity)

    @Query("SELECT * FROM UnknownEntity")
    suspend fun getAll(): List<UnknownEntity>

    @Delete
    suspend fun delete(unknownEntity: UnknownEntity)

    @Query("DELETE FROM UnknownEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM UnknownEntity WHERE id = :id")
    suspend fun getById(id: Int): UnknownEntity?


}