package com.transsion.financialassistant.search.domain

import androidx.paging.PagingData
import com.transsion.financialassistant.data.models.Frequent
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.search.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepo {

    fun onSearch(query: String): Flow<PagingData<UnifiedTransactionPersonal>>

    suspend fun getFrequentSenders(): List<Frequent>
    suspend fun getFrequentRecipients(): List<Frequent>
    suspend fun saveRecentSearch(query: String)
    fun getRecentSearches(): Flow<List<RecentSearchQuery>>

    fun deleteRecentSearch(id: Long)

}