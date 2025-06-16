package com.transsion.financialassistant.data.room.entities.search_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity)

    //delete
    @Query("DELETE FROM SearchHistoryEntity WHERE id = :id")
    suspend fun deleteSearchHistory(id: Long)

    //get total Search History counts
    @Query("SELECT COUNT(*) FROM SearchHistoryEntity")
    suspend fun getSearchHistoryCount(): Int


    @Query("SELECT * FROM SearchHistoryEntity ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<SearchHistoryEntity>>

    @Query("SELECT * FROM SearchHistoryEntity ORDER BY timestamp ASC LIMIT 1")
    suspend fun getEarliest(): SearchHistoryEntity?

    @Query("SELECT * FROM SearchHistoryEntity WHERE searchQuery = :query")
    suspend fun getSearchHistoryByQuery(query: String): SearchHistoryEntity?
}