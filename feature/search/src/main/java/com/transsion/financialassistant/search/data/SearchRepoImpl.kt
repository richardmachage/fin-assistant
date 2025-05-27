package com.transsion.financialassistant.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.Frequent
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.search.domain.SearchRepo
import com.transsion.financialassistant.search.model.RecentSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val financialAssistantDao: FinancialAssistantDao
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
        list.add(RecentSearchQuery((list.size + 1).toLong(), query))
    }

    override fun getRecentSearches(): Flow<List<RecentSearchQuery>> = flowOf(list)

    override fun deleteRecentSearch(id: Long) {
        list.removeIf { it.id == id }
        getRecentSearches()

    }


    private val list = mutableListOf(
        RecentSearchQuery(0L, "food"),
        RecentSearchQuery(1L, "Alex")
    )

}