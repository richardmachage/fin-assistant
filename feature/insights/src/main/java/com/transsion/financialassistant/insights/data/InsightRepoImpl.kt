package com.transsion.financialassistant.insights.data

import androidx.compose.ui.graphics.Color
import com.transsion.financialassistant.data.cache.AppCache
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.db.FinancialAssistantDao
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseDao
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeDao
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsDao
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyDao
import com.transsion.financialassistant.data.room.entities.move_to_pochi.MoveToPochiDao
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillDao
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyDao
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariDao
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiDao
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyDao
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariDao
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiDao
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyDao
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.insights.domain.InsightsRepo
import com.transsion.financialassistant.insights.model.InsightTimeline
import com.transsion.financialassistant.insights.model.TransactionUi
import com.transsion.financialassistant.presentation.R
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
    private val dao: FinancialAssistantDao,
    private val sendMoneyDao: SendMoneyDao,
    private val withdrawalDao: WithdrawMoneyDao,
    private val payBillDao: PayBillDao,
    private val buyGoodsDao: BuyGoodsDao,
    private val depositMoneyDao: DepositMoneyDao,
    private val receiveMoneyDao: ReceiveMoneyDao,
    private val receivePochiDao: ReceivePochiDao,
    private val sendPochiDao: SendPochiDao,
    private val buyAirtimeDao: BuyAirtimeDao,
    private val bundlesPurchaseDao: BundlesPurchaseDao,
    private val sendMshwariDao: SendMshwariDao,
    private val receiveMshwariDao: ReceiveMshwariDao,
    private val moveToPochiDao: MoveToPochiDao,
) : InsightsRepo {

    private val _categoryDistributionFlow =
        MutableStateFlow<List<CategoryDistribution>>(emptyList())
    override val categoryDistributionFlow: StateFlow<List<CategoryDistribution>>
        get() = _categoryDistributionFlow


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

    override suspend fun getTotalTransactionCost(
        startDate: String,
        endDate: String
    ): Result<Double> {
        val cacheKey = "total_transaction_cost$startDate$endDate"

        return try {
            val cache = AppCache.get<Double>(cacheKey)
            cache?.let {
                Result.success(it)
            } ?: run {
                val totalTransactionCost =
                    dao.getTotalTransactionCost(startDate = startDate, endDate = endDate) ?: 0.0
                AppCache.put(key = cacheKey, value = totalTransactionCost)
                Result.success(totalTransactionCost)
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


    /*data class CategoryDataPoint(
        val category: String,
        val amount: Double
    )*/

    /*  private fun getCategoryDistribution(
          data: List<CategoryDataPoint>
      ): List<CategoryDistribution> {
          val total = data.sumOf { it.amount }
          if (total == 0.0) return emptyList()
          return data
              .groupBy {
                  it.category
              }
              .mapValues { (_, items) -> items.sumOf { it.amount } }
              .map { (category, sum) ->
                  CategoryDistribution(
                      name = category,
                      percentage = (sum / total).toFloat(),
                      color = generateColorFromCategory(category),
                      amount = sum.toFloat()
                  )
              }
              .sortedByDescending { it.percentage } //{ it.percentage }

      }*/

    override fun getDataForCategory(
        startDate: String,
        endDate: String,
        transactionType: TransactionType,
        transactionCategory: TransactionCategory
    ): Flow<List<TransactionUi>> = flow<List<TransactionUi>> {
        val cacheKey = "data_for_category$startDate$endDate${transactionType.description}"

        val cachedData = AppCache.get<List<TransactionUi>>(cacheKey)
        if (cachedData != null) {
            emit(cachedData)
        } else {
            val data = when (transactionType) {
                TransactionType.DEPOSIT -> {
                    depositMoneyDao.getDepositMoneyTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.agentDepositedTo,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.WITHDRAWAL -> {
                    withdrawalDao.getWithdrawMoneyTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    )
                        .map {
                            TransactionUi(
                                title = it.agent,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.SEND_MONEY -> {
                    sendMoneyDao.getSendMoneyTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.sentToName,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.RECEIVE_MONEY -> {
                    receiveMoneyDao.getReceiveMoneyTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.receiveFromName,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.RECEIVE_POCHI -> {
                    receivePochiDao.getReceivePochiTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.receiveFromName,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.SEND_POCHI -> {
                    sendPochiDao.getSendPochiTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.sentToName,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.PAY_BILL -> {
                    payBillDao.getPayBillTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = it.paidToName,
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.BUY_GOODS -> {
                    buyGoodsDao.getBuyGoodsTransactionsByDate(startDate, endDate).map {
                        TransactionUi(
                            title = it.paidTo,
                            type = it.transactionType,
                            inOrOut = it.transactionCategory,
                            amount = it.amount.toString(),
                            dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                        )
                    }

                }

                TransactionType.SEND_MSHWARI -> {
                    sendMshwariDao.getSendMshwariTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        TransactionUi(
                            title = "M-SHWARI",
                            type = it.transactionType,
                            inOrOut = it.transactionCategory,
                            amount = it.amount.toString(),
                            dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                        )
                    }
                }

                TransactionType.RECEIVE_MSHWARI -> {
                    receiveMshwariDao.getReceiveMshwariTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        TransactionUi(
                            title = "M-SHWARI",
                            type = it.transactionType,
                            inOrOut = it.transactionCategory,
                            amount = it.amount.toString(),
                            dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                        )
                    }
                }

                TransactionType.AIRTIME_PURCHASE -> {
                    buyAirtimeDao.getBuyAirtimeTransactionsByDate(startDate, endDate).map {
                        TransactionUi(
                            title = "AIRTIME",
                            type = it.transactionType,
                            inOrOut = it.transactionCategory,
                            amount = it.amount.toString(),
                            dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                        )
                    }
                }

                TransactionType.BUNDLES_PURCHASE -> {
                    bundlesPurchaseDao.getBundlesPurchaseTransactionsByDate(startDate, endDate)
                        .map {
                            TransactionUi(
                                title = "DATA & BUNDLES",
                                type = it.transactionType,
                                inOrOut = it.transactionCategory,
                                amount = it.amount.toString(),
                                dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                            )
                        }
                }

                TransactionType.UNKNOWN -> {
                    emptyList()
                }

                TransactionType.MOVE_TO_POCHI -> {
                    moveToPochiDao.getRecordsByDate(startDate, endDate).map {
                        TransactionUi(
                            title = it.transactionType.description,
                            type = it.transactionType,
                            inOrOut = transactionCategory,
                            amount = it.amount.toString(),
                            dateAndTime = "${it.date.toMonthDayDate()}, ${it.time}"
                        )
                    }
                }
            }
            AppCache.put(key = cacheKey, value = data)
            emit(data)
        }
    }.catch {
        emit(emptyList())
    }

    override fun getDataPointsForCategory(
        startDate: String,
        endDate: String,
        transactionType: TransactionType
    ): Flow<List<DataPoint>> = flow {
        val cacheKey = "data_points_for_category$startDate$endDate${transactionType.description}"

        val cachedData = AppCache.get<List<DataPoint>>(cacheKey)
        if (cachedData != null) {
            emit(cachedData)
        } else {
            val dataPoints = when (transactionType) {
                TransactionType.DEPOSIT -> {
                    depositMoneyDao.getDepositMoneyTransactionsByDate(startDate, endDate)
                        .map {
                            DataPoint(
                                x = it.date,
                                y = it.amount.toFloat()
                            )
                        }

                }

                TransactionType.WITHDRAWAL -> {
                    withdrawalDao.getWithdrawMoneyTransactionsByDate(startDate, endDate).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.SEND_MONEY -> {
                    sendMoneyDao.getSendMoneyTransactionsByDate(startDate, endDate).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.RECEIVE_MONEY -> {
                    receiveMoneyDao.getReceiveMoneyTransactionsByDate(startDate, endDate).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.RECEIVE_POCHI -> {
                    receivePochiDao.getReceivePochiTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.SEND_POCHI -> {
                    sendPochiDao.getSendPochiTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.PAY_BILL -> {
                    payBillDao.getPayBillTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.BUY_GOODS -> {
                    buyGoodsDao.getBuyGoodsTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.SEND_MSHWARI -> {
                    sendMshwariDao.getSendMshwariTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.RECEIVE_MSHWARI -> {
                    receiveMshwariDao.getReceiveMshwariTransactionsByDate(
                        startDate = startDate,
                        endDate = endDate
                    ).map { DataPoint(x = it.date, y = it.amount.toFloat()) }
                }

                TransactionType.AIRTIME_PURCHASE -> {
                    buyAirtimeDao.getBuyAirtimeTransactionsByDate(startDate, endDate).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.BUNDLES_PURCHASE -> {
                    bundlesPurchaseDao.getBundlesPurchaseTransactionsByDate(startDate, endDate)
                        .map {
                            DataPoint(
                                x = it.date,
                                y = it.amount.toFloat()
                            )
                        }
                }

                TransactionType.MOVE_TO_POCHI -> {
                    moveToPochiDao.getRecordsByDate(startDate, endDate).map {
                        DataPoint(
                            x = it.date,
                            y = it.amount.toFloat()
                        )
                    }
                }

                TransactionType.UNKNOWN -> emptyList()
            }

            AppCache.put(key = cacheKey, value = dataPoints)
            emit(dataPoints)
        }
    }.catch {
        emit(emptyList())
    }

    override fun getDataPoints(
        insightCategory: InsightCategory,
        transactionCategory: TransactionCategory,
        insightTimeline: InsightTimeline

    ): Flow<List<DataPoint>> = flow {
        val startDate = insightTimeline.getTimeline().startDate
        val endDate = insightTimeline.getTimeline().endDate

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
                    val data = when (insightTimeline) {
                        InsightTimeline.TODAY -> {
                            dao.getTransactionsInForDate(startDate).map {
                                DataPoint(x = it.time, y = it.amount.toFloat())
                            }
                        }

                        InsightTimeline.WEEK -> {
                            dao.getTotalTransactionsInPerDay(
                                startDate = startDate,
                                endDate = endDate
                            ).map {
                                DataPoint(x = it.date, y = it.totalAmount.toFloat())
                            }
                        }

                        InsightTimeline.MONTH -> {
                            dao.getTotalTransactionsInPerDay(
                                startDate = startDate,
                                endDate = endDate
                            ).map {
                                DataPoint(x = it.date, y = it.totalAmount.toFloat())
                            }
                        }
                    }


                    val distributionData =
                        dao.getTotalTransactionsInPerType(startDate = startDate, endDate = endDate)
                    val totalAmount = distributionData.sumOf { it.totalAmount }.toFloat()
                    val distribution = distributionData.map {
                        CategoryDistribution(
                            name = it.transactionType.description,
                            percentage = it.totalAmount.toFloat() / totalAmount,
                            color = generateColorFromCategory(it.transactionType.description),
                            amount = it.totalAmount.toFloat(),
                            icon = when (it.transactionType) {
                                TransactionType.DEPOSIT -> com.transsion.financialassistant.presentation.R.drawable.pay_cash
                                TransactionType.WITHDRAWAL -> com.transsion.financialassistant.presentation.R.drawable.payment_01
                                TransactionType.SEND_MONEY -> com.transsion.financialassistant.presentation.R.drawable.ph_coins_bold
                                TransactionType.RECEIVE_MONEY -> com.transsion.financialassistant.presentation.R.drawable.coins_01
                                TransactionType.RECEIVE_POCHI -> com.transsion.financialassistant.presentation.R.drawable.ph_coins_bold
                                TransactionType.SEND_POCHI -> com.transsion.financialassistant.presentation.R.drawable.transaction
                                TransactionType.PAY_BILL -> com.transsion.financialassistant.presentation.R.drawable.briefcase_dollar
                                TransactionType.BUY_GOODS -> com.transsion.financialassistant.presentation.R.drawable.briefcase_dollar
                                TransactionType.SEND_MSHWARI -> com.transsion.financialassistant.presentation.R.drawable.savings
                                TransactionType.RECEIVE_MSHWARI -> com.transsion.financialassistant.presentation.R.drawable.account
                                TransactionType.AIRTIME_PURCHASE -> com.transsion.financialassistant.presentation.R.drawable.smart_phone_01
                                TransactionType.BUNDLES_PURCHASE -> com.transsion.financialassistant.presentation.R.drawable.smart_phone_01
                                TransactionType.MOVE_TO_POCHI -> com.transsion.financialassistant.presentation.R.drawable.transaction
                                TransactionType.UNKNOWN -> null
                            }
                        )
                    }

                    _categoryDistributionFlow.value = distribution
                    AppCache.put(categoryCacheKey, distribution)

                    //return the rest of the data
                    data

                }

                TransactionCategory.OUT -> {
                    val data =
                        when (insightTimeline) {
                            InsightTimeline.TODAY -> {
                                dao.getTransactionsOutForDate(startDate).map {
                                    DataPoint(x = it.time, y = it.amount.toFloat())
                                }
                            }

                            InsightTimeline.WEEK -> {
                                dao.getTotalTransactionsOutPerDay(
                                    startDate = startDate,
                                    endDate = endDate
                                ).map {
                                    DataPoint(x = it.date, y = it.totalAmount.toFloat())
                                }
                            }

                            InsightTimeline.MONTH -> {
                                dao.getTotalTransactionsOutPerDay(
                                    startDate = startDate,
                                    endDate = endDate
                                ).map {
                                    DataPoint(x = it.date, y = it.totalAmount.toFloat())
                                }
                            }
                        }

                    //update the categories
                    val distributionData =
                        dao.getTotalTransactionsOutPerType(startDate = startDate, endDate = endDate)

                    val totalAmount = distributionData.sumOf { it.totalAmount }.toFloat()
                    val distribution = distributionData.map {
                        CategoryDistribution(
                            name = it.transactionType.description,
                            percentage = it.totalAmount.toFloat() / totalAmount,
                            color = generateColorFromCategory(it.transactionType.description),
                            amount = it.totalAmount.toFloat(),
                            icon = when (it.transactionType) {
                                TransactionType.DEPOSIT -> R.drawable.pay_cash
                                TransactionType.WITHDRAWAL -> R.drawable.payment_01
                                TransactionType.SEND_MONEY -> R.drawable.ph_coins_bold
                                TransactionType.RECEIVE_MONEY -> R.drawable.coins_01
                                TransactionType.RECEIVE_POCHI -> R.drawable.ph_coins_bold
                                TransactionType.SEND_POCHI -> R.drawable.transaction
                                TransactionType.PAY_BILL -> R.drawable.briefcase_dollar
                                TransactionType.BUY_GOODS -> R.drawable.briefcase_dollar
                                TransactionType.SEND_MSHWARI -> R.drawable.savings
                                TransactionType.RECEIVE_MSHWARI -> R.drawable.account
                                TransactionType.AIRTIME_PURCHASE -> R.drawable.smart_phone_01
                                TransactionType.BUNDLES_PURCHASE -> R.drawable.smart_phone_01
                                TransactionType.MOVE_TO_POCHI -> R.drawable.transaction
                                TransactionType.UNKNOWN -> null
                            }
                        )
                    }

                    /* val categorizedData = data.map {
                         CategoryDataPoint(
                             category = it.transactionType.description,
                             amount = it.amount
                         )
                     }
                     val distribution = getCategoryDistribution(categorizedData)*/

                    _categoryDistributionFlow.value = distribution
                    // _categoryDistributionFlow.value = getCategoryDistribution(categorizedData)
                    //return the rest of the data
                    data
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