package com.transsion.financialassistant.data.room.entities.withdraw

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WithdrawMoneyDao {

    // Create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(withdrawMoneyEntity: WithdrawMoneyEntity)

    //read
    @Query("SELECT * FROM WithdrawMoneyEntity")
    fun getAll(): Flow<List<WithdrawMoneyEntity>>

    // update
    @Update
   suspend fun update(withdrawMoneyEntity: WithdrawMoneyEntity)

    //delete
    @Delete
    suspend fun delete(withdrawMoneyEntity: WithdrawMoneyEntity)
}