package com.transsion.financialassistant.data.room.db

import androidx.room.Dao
import androidx.room.Query
import com.transsion.financialassistant.data.room.models.UnifiedIncomingTransaction


/**
This will be a general Dao for the general database queries
that are not mapped to a single specific entity i.e queries with JIONs for several entities
 */
@Dao
interface FinancialAssistantDao {


    @Query(
        """
  SELECT transactionCode,phone, amount, date, time, transactionType FROM ReceiveMoneyEntity
  UNION ALL
  SELECT transactionCode,phone, amount, date, time, transactionType FROM ReceiveMshwariEntity
  UNION ALL
  SELECT transactionCode,phone, amount, date, time, transactionType FROM ReceivePochiEntity
  UNION ALL
  SELECT transactionCode,phone, amount, date, time, transactionType FROM DepositMoneyEntity
  WHERE date BETWEEN :startDate AND :endDate
  ORDER BY date DESC, time DESC
 """
    )
    suspend fun getAllMoneyInTransactions(
        startDate: String,
        endDate: String
    ): List<UnifiedIncomingTransaction>


}