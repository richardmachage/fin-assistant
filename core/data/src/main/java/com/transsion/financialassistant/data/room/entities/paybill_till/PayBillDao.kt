package com.transsion.financialassistant.data.room.entities.paybill_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PayBillDao {

    @Insert
    suspend fun insert(payBillEntity: PayBillEntity)

    @Update
    suspend fun update(payBillEntity: PayBillEntity)

    @Delete
    suspend fun delete(payBillEntity: PayBillEntity)

    @Query("SELECT * FROM PayBillEntity")
    fun getAll(): Flow<List<PayBillEntity>>



}