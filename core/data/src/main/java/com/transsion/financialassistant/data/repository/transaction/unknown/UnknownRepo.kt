package com.transsion.financialassistant.data.repository.transaction.unknown

import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntity

interface UnknownRepo {

    suspend fun insertUnknownTransaction(message: String)

    suspend fun deleteById(id: Int)

    suspend fun getById(id: Int): UnknownEntity?

    suspend fun getAll(): List<UnknownEntity>

    suspend fun delete(batchList: List<UnknownEntity>)

}