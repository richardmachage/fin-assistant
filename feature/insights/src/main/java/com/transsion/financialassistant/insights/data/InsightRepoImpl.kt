package com.transsion.financialassistant.insights.data

import androidx.compose.ui.graphics.Color
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.insights.domain.InsightsRepo
import com.transsion.financialassistant.insights.model.InsightCategory
import com.transsion.financialassistant.presentation.components.graphs.model.CategoryDistribution
import com.transsion.financialassistant.presentation.components.graphs.model.DataPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.abs

class InsightRepoImpl @Inject constructor(
    private val dao: FinancialAssistantDao
) : InsightsRepo {

    private val _categoryDistributionFlow =
        MutableStateFlow<List<CategoryDistribution>>(emptyList())
    override val categoryDistributionFlow: StateFlow<List<CategoryDistribution>>
        get() = _categoryDistributionFlow
    //val categoryDistributionFlow: StateFlow<List<CategoryDistribution>> = _categoryDistributionFlow


    override suspend fun getTotalMoneyIn(startDate: String, endDate: String): Result<Double> {
        val cacheKey = "total_money_in$startDate$endDate"
        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyIn = dao.getTotalMoneyInAmount(startDate, endDate) ?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyIn)
                Result.success(totalMoneyIn)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTotalMoneyOut(startDate: String, endDate: String): Result<Double> {
        val cacheKey = "total_money_out$startDate$endDate"
        return try {
            // First check if exists in cache
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val totalMoneyOut = dao.getTotalMoneyOutAmount(startDate, endDate) ?: 0.0
                AppCache.put(key = cacheKey, value = totalMoneyOut)
                Result.success(totalMoneyOut)
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNumOfTransactionsIn(startDate: String, endDate: String): Result<Int> {
        val cacheKey = "number_of_transactions_in$startDate$endDate"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Int>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val numOfTransactions = dao.getNumberOfTransactionsIn(startDate, endDate) ?: 0

                AppCache.put(key = cacheKey, value = numOfTransactions)
                Result.success(numOfTransactions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNumOfTransactionsOut(startDate: String, endDate: String): Result<Int> {
        val cacheKey = "number_of_transactions_out$startDate$endDate"

        return try {
            // First check if exists in cache
            val cache = AppCache.get<Int>(cacheKey)
            cache?.let { fromCache ->
                Result.success(fromCache)
            } ?: run {
                //not in cache, fetch from DB and insert in cache
                val numOfTransactions = dao.getNumberOfTransactionsOut(startDate, endDate) ?: 0

                AppCache.put(key = cacheKey, value = numOfTransactions)
                Result.success(numOfTransactions)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getTotalTransactions(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }


    data class CategoryDataPoint(
        val category: String,
        val amount: Double
    )

    fun getCategoryDistribution(
        data: List<CategoryDataPoint>
    ): List<CategoryDistribution> {
        val total = data.sumOf { it.amount }
        if (total == 0.0) return emptyList()
        return data
            .groupBy { it.category }
            .mapValues { (_, items) -> items.sumOf { it.amount } }
            .map { (category, sum) ->
                CategoryDistribution(
                    name = category,
                    percentage = (sum / total).toFloat(),
                    color = generateColorFromCategory(category),
                    amount = sum.toFloat()
                )
            }
            .sortedBy { it.percentage }

    }

    override fun getDataPoints(
        insightCategory: InsightCategory,
        startDate: String,
        endDate: String,
        transactionCategory: TransactionCategory
    ): Flow<List<DataPoint>> = flow {
        val cacheKey =
            "data_points${insightCategory.name}$startDate$endDate${transactionCategory.name}"
        val categoryCacheKey =
            "category_distribution${insightCategory.name}$startDate$endDate${transactionCategory.name}"

        val cachedData = AppCache.get<List<DataPoint>>(cacheKey)
        val cachedCategories = AppCache.get<List<CategoryDistribution>>(categoryCacheKey)

        if (cachedData != null && cachedCategories != null) {
            _categoryDistributionFlow.value = cachedCategories
            emit(cachedData)
        } else {
            val dataPoints = when (transactionCategory) {
                TransactionCategory.IN -> {
                    val data =
                        dao.getAllMoneyInTransactions(startDate = startDate, endDate = endDate)

                    //update the categories
                    val categorizedData = data.map {
                        CategoryDataPoint(
                            category = it.transactionType.description,
                            amount = it.amount
                        )
                    }
                    val distribution = getCategoryDistribution(categorizedData)
                    _categoryDistributionFlow.value = distribution
                    AppCache.put(categoryCacheKey, distribution)


                    //return the rest of the data
                    data.map {
                        DataPoint(x = it.date, y = it.amount.toFloat())
                    }
                }

                TransactionCategory.OUT -> {
                    val data =
                        dao.getAllMoneyOutTransactions(startDate = startDate, endDate = endDate)

                    //update the categories
                    val categorizedData = data.map {
                        CategoryDataPoint(
                            category = it.transactionType.description,
                            amount = it.amount
                        )
                    }
                    _categoryDistributionFlow.value = getCategoryDistribution(categorizedData)

                    data.map {
                        DataPoint(x = it.date, y = it.amount.toFloat())
                    }

                }
            }
            AppCache.put(key = cacheKey, value = dataPoints)
            emit(dataPoints)
        }

    }.catch {
        emit(emptyList())
    }


    fun generateColorFromCategory(category: String): Color {
        val hash = abs(category.hashCode())

        // Generate hue (0..360)
        val hue = hash % 360
        val saturation = 0.6f  // Nice vivid color
        val lightness = 0.5f   // Medium brightness

        return hslToColor(hue.toFloat(), saturation, lightness)
    }

    // Helper to convert HSL to Color
    private fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
        val c = (1 - kotlin.math.abs(2 * lightness - 1)) * saturation
        val x = c * (1 - kotlin.math.abs((hue / 60f) % 2 - 1))
        val m = lightness - c / 2

        val (r1, g1, b1) = when {
            hue < 60 -> Triple(c, x, 0f)
            hue < 120 -> Triple(x, c, 0f)
            hue < 180 -> Triple(0f, c, x)
            hue < 240 -> Triple(0f, x, c)
            hue < 300 -> Triple(x, 0f, c)
            else -> Triple(c, 0f, x)
        }

        return Color(
            red = (r1 + m),
            green = (g1 + m),
            blue = (b1 + m),
            alpha = 1f
        )
    }
}