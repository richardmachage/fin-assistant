package com.transsion.financialassistant.data.room.entities.paybill_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PayBillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(payBillEntity: PayBillEntity)

    @Update
    suspend fun update(payBillEntity: PayBillEntity)

    @Delete
    suspend fun delete(payBillEntity: PayBillEntity)

    @Query("SELECT * FROM PayBillEntity")
    fun getAll(): Flow<List<PayBillEntity>>

    @Query("SELECT * FROM PayBillEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getPayBillTransactionsByDate(startDate: String, endDate: String): List<PayBillEntity>
}