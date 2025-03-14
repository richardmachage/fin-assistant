package com.transsion.financialassistant.data.models

enum class TransactionType(val description: String) {
    DEPOSIT("Money Deposit"),
    WITHDRAWAL("Money Withdrawal"),
    SEND_MONEY("Send Money"),
    RECEIVE_MONEY("Receive Money"),
    RECEIVE_POCHI("Pochi la Biashara"),
    PAY_BILL("Pay Bill"),
    BUY_GOODS("Buy Goods Till"),
    SEND_MSHWARI("Send money to Mshwari"),
    RECEIVE_MSHWARI("Receive money from Mshwari"),
    AIRTIME_PURCHASE("Purchase Airtime"),
    BUNDLES_PURCHASE("Purchase Data Bundles");

    fun getRegex(): Regex {
        return when (this) {
            DEPOSIT -> "".toRegex()
            WITHDRAWAL -> "".toRegex()
            SEND_MONEY -> "".toRegex()
            RECEIVE_MONEY -> "".toRegex()
            RECEIVE_POCHI -> "".toRegex()
            PAY_BILL -> "".toRegex()
            BUY_GOODS -> "".toRegex()
            SEND_MSHWARI -> "".toRegex()
            RECEIVE_MSHWARI -> "".toRegex()
            AIRTIME_PURCHASE -> "".toRegex()
            BUNDLES_PURCHASE -> "".toRegex()
        }
    }
}


