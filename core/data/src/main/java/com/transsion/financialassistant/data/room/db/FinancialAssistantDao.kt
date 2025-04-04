package com.transsion.financialassistant.data.room.db

import androidx.room.Dao
import androidx.room.Query


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
        ORDER BY date DESC, time DESC
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
 ORDER BY date DESC, time DESC
 """
 )
 suspend fun getAllMoneyOutTransactions(
  startDate: String,
  endDate: String
 ): List<UnifiedOutGoingTransaction>

 @Query(
  """
    SELECT SUM(amount) FROM unifiedincomingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
 )
 suspend fun getTotalMoneyInAmount(
  startDate: String,
  endDate: String
 ): Double?

 @Query(
  """
    SELECT SUM(amount) FROM unifiedoutgoingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
 )
 suspend fun getTotalMoneyOutAmount(
  startDate: String,
  endDate: String
 ): Double?


 @Query(
  """
    SELECT COUNT (*) FROM unifiedincomingtransaction
    WHERE  date BETWEEN :startDate AND :endDate
    """
 )
 suspend fun getNumberOfTransactionsIn(
  startDate: String,
  endDate: String
 ): Int?

 @Query(
  """
  SELECT COUNT (*) FROM unifiedoutgoingtransaction
  WHERE  date BETWEEN :startDate AND :endDate
 """
 )
 suspend fun getNumberOfTransactionsOut(
  startDate: String,
  endDate: String
 ): Int?



}