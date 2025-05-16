package com.transsion.financialassistant.data.room.entities.buygoods_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyGoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(buyGoodsEntity: BuyGoodsEntity)

    @Update
    suspend fun update(buyGoodsEntity: BuyGoodsEntity)

    @Delete
    suspend fun delete(buyGoodsEntity: BuyGoodsEntity)

    @Query("SELECT * FROM BuyGoodsEntity")
    fun getAll(): Flow<List<BuyGoodsEntity>>

    @Query("SELECT * FROM BuyGoodsEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getBuyGoodsTransactionsByDate(startDate: String, endDate: String): List<BuyGoodsEntity>

}