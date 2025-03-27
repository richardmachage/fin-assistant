package com.transsion.financialassistant.data.room.entities.receive_mshwari

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveMshwariDao {
    // create
    @Insert
    suspend fun insert(receiveMshwari: ReceiveMshwariEntity)

    // update
    @Update
    suspend fun update(sendMshwariEntity: SendMshwariEntity)

    // delete an item
    @Delete
    suspend fun delete(sendMshwariEntity: SendMshwariEntity)

    // read
    @Query("SELECT * FROM receive_mshwari_table")
    fun getAll(): Flow<List<ReceiveMshwariEntity>>

    // delete all
    @Query("DELETE FROM receive_mshwari_table")
    suspend fun deleteAll()

    // select an item by transactionCode
    @Query("SELECT * FROM receive_mshwari_table WHERE transactionCode = :transactionCode")
    suspend fun getByTransactionCode(transactionCode: String): ReceiveMshwariEntity?

}