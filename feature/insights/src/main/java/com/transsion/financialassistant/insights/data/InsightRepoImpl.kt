package com.transsion.financialassistant.insights.data

import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyDao
import com.transsion.financialassistant.insights.domain.InsightsRepo
import javax.inject.Inject

class InsightRepoImpl @Inject constructor(
    private val sendMoneyDao: SendMoneyDao
) : InsightsRepo {
    override fun getTotalMoneyIn(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

    override fun getMoneyOut(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

    override fun getTransactionsIn(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

    override fun getTransactionsOut(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

    override fun getTotalTransactions(startDate: String, endDate: String) {
        TODO("Not yet implemented")
    }

}