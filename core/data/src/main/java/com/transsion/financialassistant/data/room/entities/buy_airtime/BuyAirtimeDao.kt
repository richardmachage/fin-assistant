package com.transsion.financialassistant.data.room.entities.buy_airtime

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyAirtimeDao {
    // Create
    @Insert
    suspend fun insert(buyAirtimeEntity: BuyAirtimeEntity)

    // update
    @Update
    suspend fun update(buyAirtimeEntity: BuyAirtimeEntity)

    //delete
    @Delete
    suspend fun delete(buyAirtimeEntity: BuyAirtimeEntity)

    //order by date
    @Query("SELECT * FROM BuyAirtimeEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getBuyAirtimeTransactionsByDate(startDate: String, endDate: String): List<BuyAirtimeEntity>

    //read
    @Query("SELECT * FROM BuyAirtimeEntity")
    fun getAll(): Flow<List<BuyAirtimeEntity>>
}