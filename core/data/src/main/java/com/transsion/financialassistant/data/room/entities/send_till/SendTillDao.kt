package com.transsion.financialassistant.data.room.entities.send_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SendTillDao {
    //insert
    @Insert
    suspend fun insert(sendTillEntity: SendTillEntity)

    //update
    @Update
    suspend fun update(sendTillEntity: SendTillEntity)

    //delete
    @Delete
    suspend fun delete(sendTillEntity: SendTillEntity)

    //Read
    @Query("SELECT * FROM SendTillEntity ORDER BY date DESC, time DESC")
    suspend fun getAllReceiveTill(): List<SendTillEntity>

    @Query("SELECT * FROM SendTillEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceiveTillTransactionsByDate(
        startDate: String,
        endDate: String
    ): List<SendTillEntity>

    @Query(
        """
            SELECT COUNT(transactionCode) FROM SendTillEntity
        """
    )
    fun getNumOfAllTransactions(): Flow<Int>

}