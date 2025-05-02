package com.transsion.financialassistant.data.room.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.transsion.financialassistant.data.models.DailyTransactionTotal
import com.transsion.financialassistant.data.models.DailyTransactionTypeTotal
import com.transsion.financialassistant.data.models.DailyTransactionsTime
import com.transsion.financialassistant.data.room.views.personal.UnifiedIncomingTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedOutGoingTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import kotlinx.coroutines.flow.Flow


/**
This will be a general Dao for the general database queries
that are not mapped to a single specific entity i.e queries with JIONs for several entities
 */
@Dao
interface FinancialAssistantDao {

    @Query(
        """
        SELECT * FROM UnifiedIncomingTransaction
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date ASC, time ASC
    """
    )
    suspend fun getAllMoneyInTransactions(
        startDate: String,
        endDate: String
    ): List<UnifiedIncomingTransaction>

    @Query(
        """
  SELECT * FROM UnifiedOutGoingTransaction
 WHERE date BETWEEN :startDate AND :endDate
 ORDER BY date ASC, time ASC
 """
    )
    suspend fun getAllMoneyOutTransactions(
        startDate: String,
        endDate: String
    ): List<UnifiedOutGoingTransaction>


    @Query(
        """
 SELECT SUM(transactionCost) FROM unifiedoutgoingtransaction WHERE date BETWEEN :startDate AND :endDate
 """
    )
    fun getTotalTransactionCost(
        startDate: String,
        endDate: String
    ): Flow<Double>

    @Query(
        """
    SELECT SUM(amount) FROM unifiedincomingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalMoneyInAmount(
        startDate: String,
        endDate: String
    ): Flow<Double>

    // pochi money in
    @Query(
        """
            SELECT SUM(businessBalance) FROM ReceivePochiEntity
            WHERE date BETWEEN :startDate AND :endDate
        """
    )
    suspend fun getTotalPochiMoneyInAmount(
        startDate: String,
        endDate: String
    ): Double?

    @Query(
        """
    SELECT SUM(amount + transactionCost) FROM unifiedoutgoingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalMoneyOutAmount(
        startDate: String,
        endDate: String
    ): Flow<Double>


    @Query(
        """
    SELECT COUNT (*) FROM unifiedincomingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
    )
    fun getNumberOfTransactionsIn(
        startDate: String,
        endDate: String
    ): Flow<Int?>

    @Query(
        """
  SELECT COUNT (*) FROM unifiedoutgoingtransaction
  WHERE  date BETWEEN :startDate AND :endDate
 """
    )
    fun getNumberOfTransactionsOut(
        startDate: String,
        endDate: String
    ): Flow<Int?>


    @Query(
        """
        SELECT time, amount
        FROM UnifiedOutGoingTransaction
        WHERE date = :date
        ORDER BY time ASC
    """
    )
    suspend fun getTransactionsOutForDate(
        date: String,
    ): List<DailyTransactionsTime>

    @Query(
        """
        SELECT time, amount
        FROM UnifiedIncomingTransaction
        WHERE date = :date
        ORDER BY time ASC
    """
    )
    suspend fun getTransactionsInForDate(
        date: String,
    ): List<DailyTransactionsTime>


    @Query(
        """
        SELECT date,time, SUM(amount) as totalAmount
        FROM UnifiedIncomingTransaction
        WHERE date BETWEEN :startDate AND :endDate
        GROUP BY date
        ORDER BY date ASC
    """
    )
    suspend fun getTotalTransactionsInPerDay(
        startDate: String,
        endDate: String
    ): List<DailyTransactionTotal>


    @Query(
        """
        SELECT date,time, SUM(amount) as totalAmount
        FROM UnifiedOutGoingTransaction
        WHERE date BETWEEN :startDate AND :endDate
        GROUP BY date
        ORDER BY date ASC
    """
    )
    suspend fun getTotalTransactionsOutPerDay(
        startDate: String,
        endDate: String
    ): List<DailyTransactionTotal>

    @Query(
        """
   SELECT transactionType, SUM(amount) AS totalAmount
FROM UnifiedIncomingTransaction
WHERE date BETWEEN :startDate AND :endDate
GROUP BY transactionType
ORDER BY totalAmount DESC;

  """
    )
    suspend fun getTotalTransactionsInPerType(
        startDate: String,
        endDate: String
    ): List<DailyTransactionTypeTotal>


    @Query(
        """
   SELECT transactionType, SUM(amount) AS totalAmount
FROM UnifiedOutGoingTransaction
WHERE date BETWEEN :startDate AND :endDate
GROUP BY transactionType
ORDER BY totalAmount DESC;

  """
    )
    suspend fun getTotalTransactionsOutPerType(
        startDate: String,
        endDate: String
    ): List<DailyTransactionTypeTotal>


    /** This is a general All Transactions Dao regardless of Time of the Transactions
     */
    @Query(
        """
            SELECT SUM(amount) FROM unifiedincomingtransaction

    """
    )
    suspend fun getAllTransactionMoneyInAmount(): Double?


    @Query(
        """
        SELECT SUM(amount + transactionCost) FROM unifiedoutgoingtransaction
    """
    )

    suspend fun getAllTransactionMoneyOutAmount(): Double?

    @Query(
        """
            SELECT COUNT(*) FROM UnifiedIncomingTransaction
        """
    )

    suspend fun getNumberofAllTransactionsIn(): Int?

    @Query(
        """
            SELECT COUNT(*) FROM UnifiedOutGoingTransaction
        """
    )
    suspend fun getNumberofAllTransactionsOut(): Int?


    /**
    This query returns all transaction from db ordered by date, it combines both incoming and outgoing transactions
     */
    @Query(
        """
        SELECT * FROM UnifiedTransactionPersonal
        ORDER BY date DESC, time DESC
    """
    )
    fun getAllTransactions(): PagingSource<Int, UnifiedTransactionPersonal>


    /** This query returns recent 10 transactions from db ordered by date, it combines both incoming and outgoing transactions
     */

    @Query(
        """
        SELECT * FROM UnifiedTransactionPersonal
        ORDER BY date DESC, time DESC
        LIMIT 10
        """
    )
    fun getRecentTransactions(): Flow<List<UnifiedTransactionPersonal>>


    /**Get Mpesa balance*/
    @Query(
        """
        SELECT mpesaBalance FROM UnifiedTransactionPersonal 
        ORDER BY date DESC, time DESC  
        LIMIT 1
            """
    )
    fun getMpesaBalance(): Flow<Double>


    @Query(
        """
            SELECT COUNT(transactionCode) FROM UnifiedTransactionPersonal
        """
    )
    fun getNumOfAllTransactions(): Flow<Int>
}