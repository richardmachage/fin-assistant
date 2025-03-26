package com.transsion.financialassistant.data.room.entities.buygoods_till

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyGoodsDao {
    @Insert
    suspend fun insert(buyGoodsEntity: BuyGoodsEntity)

    @Update
    suspend fun update(buyGoodsEntity: BuyGoodsEntity)

    @Delete
    suspend fun delete(buyGoodsEntity: BuyGoodsEntity)

    @Query("SELECT * FROM BuyGoodsEntity")
    fun getAll(): Flow<List<BuyGoodsEntity>>


}