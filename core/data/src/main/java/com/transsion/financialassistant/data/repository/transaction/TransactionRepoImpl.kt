package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType

class TransactionRepoImpl : TransactionRepo {
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
}