package com.transsion.financialassistant.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.Frequent
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.entities.search_history.SearchHistoryDao
import com.transsion.financialassistant.data.room.entities.search_history.SearchHistoryEntity
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.search.domain.SearchRepo
import com.transsion.financialassistant.search.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val financialAssistantDao: FinancialAssistantDao,
    private val searchHistoryDao: SearchHistoryDao
) : SearchRepo {
    override fun onSearch(query: String): Flow<PagingData<UnifiedTransactionPersonal>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                financialAssistantDao.searchAllTransactions(query = query.trim().lowercase())
            }
        ).flow
    }

    override suspend fun getFrequentSenders(): List<Frequent> {
        val cacheKey = "FrequentSenders"

        return AppCache.get<List<Frequent>>(key = cacheKey)
            ?: financialAssistantDao.getFrequentSenders().also {
                AppCache.put(key = cacheKey, value = it)
            }
    }


    override suspend fun getFrequentRecipients(): List<Frequent> {
        val cacheKey = "FrequentRecipients"

        return AppCache.get<List<Frequent>>(key = cacheKey)
            ?: financialAssistantDao.getFrequentRecipients().also {
                AppCache.put(key = cacheKey, value = it)
            }

    }

    override suspend fun saveRecentSearch(query: String) {

        //check count
        val count = searchHistoryDao.getSearchHistoryCount()

        if (count < 6) {
            //check if it already exists to avoid duplicates
            val exists = searchHistoryDao.getSearchHistoryByQuery(query.trim())
            if (exists != null) return

            //insert
            searchHistoryDao.insertSearchHistory(
                SearchHistoryEntity(searchQuery = query)
            )
        } else {
            //check if it already exists to avoid duplicates
            val exists = searchHistoryDao.getSearchHistoryByQuery(query.trim())
            if (exists != null) return

            //then delete the earliest one first
            val earliest = searchHistoryDao.getEarliest()
            earliest?.let {
                searchHistoryDao.deleteSearchHistory(it.id)
            }

            //then insert
            searchHistoryDao.insertSearchHistory(
                SearchHistoryEntity(searchQuery = query)
            )
        }
    }

    override fun getRecentSearches(): Flow<List<RecentSearchQuery>> {
        return searchHistoryDao.getAllHistory().map(::mapToRecentSearchQueries)
    }

    private fun mapToRecentSearchQueries(
        entities: List<SearchHistoryEntity>
    ): List<RecentSearchQuery> {
        return entities.map { RecentSearchQuery(it.id, it.searchQuery) }
    }

    override suspend fun deleteRecentSearch(id: Long) {
        searchHistoryDao.deleteSearchHistory(id)
    }


}