package com.transsion.financialassistant.data.room.views.business

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.transsion.financialassistant.data.models.DailyTransactionTotal
import com.transsion.financialassistant.data.models.DailyTransactionsTime
import kotlinx.coroutines.flow.Flow

@Dao
interface UnifiedTransactionsBusinessDao {

    @Query("SELECT * FROM UnifiedTransactionBusiness ORDER BY date DESC, time DESC")
    fun getAllTransactions(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness ORDER BY date ASC, time ASC")
    fun getAllTransactionsReverse(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'IN' ORDER BY date DESC, time DESC")
    fun getAllTransactionsIn(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query(
        """
        SELECT time, amount
        FROM UnifiedIncomingTransactionsBusiness
        WHERE date = :date
        ORDER BY time ASC
    """
    )
    suspend fun getTransactionsInForDate(
        date: String,
    ): List<DailyTransactionsTime>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'IN' AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsInForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'IN' ORDER BY date ASC, time ASC")
    fun getAllTransactionsInReverse(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'OUT' ORDER BY date DESC, time DESC")
    fun getAllTransactionsOut(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'OUT' AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsOutForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE transactionCategory = 'OUT' ORDER BY date ASC, time ASC")
    fun getAllTransactionsOutReverse(): PagingSource<Int, UnifiedTransactionBusiness>

    @Query("SELECT * FROM UnifiedTransactionBusiness WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionBusiness>

    @Query(
        """
            SELECT COUNT(transactionCode) FROM UnifiedTransactionBusiness
        """
    )
    fun getNumOfAllTransactions(): Flow<Int>

    /**Get Business balance*/
    @Query(
        """
        SELECT mpesaBalance FROM UnifiedTransactionBusiness 
        ORDER BY date DESC, time DESC  
        LIMIT 1
            """
    )
    fun getBusinessBalance(): Flow<Double>


    @Query(
        """
        SELECT * FROM UnifiedTransactionBusiness
        ORDER BY date DESC, time DESC
        LIMIT 10
        """
    )
    fun getRecentTransactions(): Flow<List<UnifiedTransactionBusiness>>


    @Query(
        """
    SELECT SUM(amount) FROM UnifiedTransactionBusiness
    WHERE  transactionCategory = 'IN' AND date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalMoneyInAmount(
        startDate: String,
        endDate: String
    ): Flow<Double>


    @Query(
        """
    SELECT SUM(amount) FROM UnifiedTransactionBusiness
    WHERE transactionCategory = 'OUT' AND  date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalMoneyOutAmount(
        startDate: String,
        endDate: String
    ): Flow<Double>


    @Query(
        """
    SELECT COUNT (*) FROM UnifiedIncomingTransactionsBusiness
    WHERE  date BETWEEN :startDate AND :endDate
    """
    )
    fun getNumberOfTransactionsIn(
        startDate: String,
        endDate: String
    ): Flow<Int?>

    @Query(
        """
  SELECT COUNT (*) FROM UnifiedOutgoingTransactionsBusiness
  WHERE  date BETWEEN :startDate AND :endDate
 """
    )
    fun getNumberOfTransactionsOut(
        startDate: String,
        endDate: String
    ): Flow<Int?>


    @Query(
        """
        SELECT date,time, SUM(amount) as totalAmount
        FROM UnifiedIncomingTransactionsBusiness
        WHERE date BETWEEN :startDate AND :endDate
        GROUP BY date
        ORDER BY date ASC
    """
    )
    suspend fun getTotalTransactionsInPerDay(
        startDate: String,
        endDate: String
    ): List<DailyTransactionTotal>

}