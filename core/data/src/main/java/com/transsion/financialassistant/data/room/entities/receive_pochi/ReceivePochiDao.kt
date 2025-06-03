package com.transsion.financialassistant.data.room.entities.receive_pochi

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceivePochiDao {
    // create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receivePochiEntity: ReceivePochiEntity)

    // delete
    @Delete
    suspend fun delete(receivePochiEntity: ReceivePochiEntity)

    // update
    @Update
    suspend fun update(receivePochiEntity: ReceivePochiEntity)

    // read
    @Query("SELECT * FROM ReceivePochiEntity")
    fun getAll(): Flow<List<ReceivePochiEntity>>


    @Query("SELECT * FROM ReceivePochiEntity ORDER BY date DESC, time DESC")
    fun getAllPaged(): PagingSource<Int, ReceivePochiEntity>


    @Query("SELECT * FROM ReceivePochiEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceivePochiTransactionsByDate(startDate: String, endDate: String): List<ReceivePochiEntity>

    @Query(
        """
        SELECT * FROM ReceivePochiEntity
        ORDER BY date DESC, time DESC
        LIMIT 10
        """
    )
    fun getRecentTransactions(): Flow<List<ReceivePochiEntity>>


    /**Get Mpesa business balance*/
    @Query(
        """
        SELECT businessBalance FROM ReceivePochiEntity 
        ORDER BY date DESC, time DESC  
        LIMIT 1
            """
    )
    fun getMpesaBalance(): Flow<Double>


    @Query(
        """
            SELECT COUNT(transactionCode) FROM ReceivePochiEntity
        """
    )
    fun getNumOfAllTransactions(): Flow<Int>

}