package com.transsion.financialassistant.data.room.entities.receive_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveTillDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReceiveTill(receiveTill: ReceiveTillEntity)

    //Delete
    @Delete
    suspend fun deleteReceiveTill(receiveTill: ReceiveTillEntity)

    //update
    @Update
    suspend fun updateReceiveTill(receiveTill: ReceiveTillEntity)

    //Read
    @Query("SELECT * FROM ReceiveTillEntity ORDER BY date DESC, time DESC")
    suspend fun getAllReceiveTill(): List<ReceiveTillEntity>


    @Query("SELECT * FROM ReceiveTillEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceiveTillTransactionsByDate(
        startDate: String,
        endDate: String
    ): List<ReceiveTillEntity>

    @Query(
        """
            SELECT COUNT(transactionCode) FROM ReceiveTillEntity
        """
    )
    fun getNumOfAllTransactions(): Flow<Int>


}