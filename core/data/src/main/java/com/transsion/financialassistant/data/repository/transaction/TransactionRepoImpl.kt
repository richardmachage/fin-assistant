package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor() : TransactionRepo {
    override fun getTransactionType(message: String): TransactionType =
        when {
            TransactionType.RECEIVE_MONEY.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MONEY

            TransactionType.BUNDLES_PURCHASE.getRegex()
                .matches(message) -> TransactionType.BUNDLES_PURCHASE

            TransactionType.AIRTIME_PURCHASE.getRegex()
                .matches(message) -> TransactionType.AIRTIME_PURCHASE

            TransactionType.DEPOSIT.getRegex()
                .matches(message) -> TransactionType.DEPOSIT

            TransactionType.WITHDRAWAL.getRegex()
                .matches(message) -> TransactionType.WITHDRAWAL

            TransactionType.PAY_BILL.getRegex()
                .matches(message) -> TransactionType.PAY_BILL

            TransactionType.SEND_MONEY.getRegex()
                .matches(message) -> TransactionType.SEND_MONEY

            TransactionType.RECEIVE_MONEY.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MONEY

            TransactionType.BUY_GOODS.getRegex()
                .matches(message) -> TransactionType.BUY_GOODS

            TransactionType.SEND_MSHWARI.getRegex().matches(message) -> TransactionType.SEND_MSHWARI

            TransactionType.RECEIVE_POCHI.getRegex()
                .matches(message) -> TransactionType.RECEIVE_POCHI

            TransactionType.RECEIVE_MSHWARI.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MSHWARI

            TransactionType.SEND_POCHI.getRegex()
                .matches(message) -> TransactionType.SEND_POCHI
            else -> TransactionType.UNKNOWN
        }

    override fun parseSendMoneyMessage(message: String, phone: String): SendMoneyEntity? {

        val match = TransactionType.SEND_MONEY.getRegex().find(message) ?: return null
        val groups = match.groupValues

        return SendMoneyEntity(
            transactionCode = groups[1],
            phone = phone,
            sentToName = groups[3],
            sentToPhone = groups[4],
            amount = groups[2].replace(",", "").toDouble(),
            mpesaBalance = groups[7].replace(",", "").toDouble(),
            transactionCost = groups[8].replace(",", "").toDouble(),
            date = groups[5],
            time = groups[6]
        )
    }
}