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
    BUNDLES_PURCHASE("Purchase Data Bundles"),
    RECEIVE_MONEY_BANK("Received from Bank");

    fun getRegex(): Regex {
        return when (this) {
            DEPOSIT -> """([A-Z0-9]+) Confirmed\.\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*Give Ksh([\d,]+\.?\d{0,2}) cash to ([\w\s]+)\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            WITHDRAWAL -> """([A-Z0-9]+) Confirmed\.\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*Withdraw Ksh([\d,]+\.?\d{0,2}) from\s*(\d+) - ([\w\s]+)\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            SEND_MONEY -> """([A-Z0-9]+) Confirmed\.\s*Ksh([\d,]+\.?\d{0,2}) sent to ([\w\s]+) (\d+) on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            RECEIVE_MONEY -> """([A-Z0-9]+) Confirmed\.\s*You have received Ksh([\d,]+\.?\d{0,2}) from\s*([\w\s]+) (\d+)\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            RECEIVE_POCHI -> "".toRegex()
            RECEIVE_MONEY_BANK -> """([A-Z0-9]+) Confirmed\.\s*You have received Ksh([\d,]+\.?\d{0,2}) from\s*([\d]+ - [\w\s]+)\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            PAY_BILL -> """([A-Z0-9]+) Confirmed\. Ksh([\d,]+\.?\d{0,2}) sent to\s*([\w\s]+) AC for account (\d+)\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            BUY_GOODS -> """([A-Z0-9]+) Confirmed\.\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*Ksh([\d,]+\.?\d{0,2}) received from\s*(\d+) ([\w\s]+)\.\s*New Account balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            SEND_MSHWARI -> """([A-Z0-9]+) Confirmed\. Ksh([\d,]+\.?\d{0,2}) transferred to M-Shwari account\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\.\s*M-PESA balance is Ksh([\d,]+\.?\d{0,2}), new M-Shwari account balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            RECEIVE_MSHWARI -> """([A-Z0-9]+) Confirmed\. You have transferred Ksh([\d,]+\.?\d{0,2}) from your M-Shwari account\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\.\s*M-Shwari balance is Ksh([\d,]+\.?\d{0,2})\.\s*M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            AIRTIME_PURCHASE -> """([A-Z0-9]+) confirmed\. You bought Ksh([\d,]+\.?\d{0,2}) of airtime\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            BUNDLES_PURCHASE -> "".toRegex()
        }
    }
}


