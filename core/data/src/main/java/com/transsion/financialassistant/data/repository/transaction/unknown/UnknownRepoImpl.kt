package com.transsion.financialassistant.data.repository.transaction.unknown

import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntity
import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntityDao
import javax.inject.Inject

class UnknownRepoImpl @Inject constructor(
    private val dao: UnknownEntityDao
) : UnknownRepo {
    override suspend fun insertUnknownTransaction(message: String) {
        try {
            dao.insert(
                UnknownEntity(
                    message = message
                )
            )
        } catch (e: Exception) {
            //TODO Handle Exception if need be

        }
    }

    override suspend fun deleteById(id: Int) {
        try {
            dao.deleteById(id)
        } catch (e: Exception) {
            //TODO Handle Exception if need be
        }
    }

    override suspend fun getById(id: Int): UnknownEntity? {
        return try {
            dao.getById(id)
        } catch (e: Exception) {
            //TODO Handle Exception if need be
            null
        }
    }

    override suspend fun getAll(): List<UnknownEntity> {
        return try {
            dao.getAll()
        } catch (e: Exception) {
            //TODO Handle Exception if need be
            emptyList()
        }
    }
}