package com.transsion.financialassistant.data.models

enum class TransactionTypesTest(val description: String, private val pattern: String) {
    DEPOSIT("Money Deposit", """(?<transactionId>[A-Z0-9]+) Confirmed\.\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*Give Ksh(?<amount>[\d,]+\.?\d{0,2}) cash to (?<recipientName>[\w\s]+)\s*New M-PESA balance is Ksh(?<balance>[\d,]+\.?\d{0,2})"""),
    WITHDRAWAL("Money Withdrawal", """(?<transactionId>[A-Z0-9]+) Confirmed\.\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*Withdraw Ksh(?<amount>[\d,]+\.?\d{0,2}) from\s*(?<agentNumber>\d+) - (?<agentName>[\w\s]+)\s*New M-PESA balance is Ksh(?<balance>[\d,]+\.?\d{0,2})"""),
    SEND_MONEY("Send Money", """(<transactionId>[A-Z0-9]+) Confirmed\.\s*Ksh(<amount>[\d,]+\.?\d{0,2}) sent to (<recipientName>[\w\s]+) (<recipientNumber>\d+) on (<date>\d{1,2}/\d{1,2}/\d{2,4}) at (<time>\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh(<balance>[\d,]+\.?\d{0,2})"""),
    RECEIVE_MONEY("Receive Money", """(?<transactionId>[A-Z0-9]+) Confirmed\.\s*You have received Ksh(?<amount>[\d,]+\.?\d{0,2}) from (?<senderName>[\w\s]+) (?<senderNumber>\d+)\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh(?<balance>[\d,]+\.?\d{0,2})"""),
    //RECEIVE_POCHI("Pochi la Biashara", null),
    PAY_BILL("Pay Bill", """(?<transactionId>[A-Z0-9]+) Confirmed\. Ksh(?<amount>[\d,]+\.?\d{0,2}) sent to (?<businessName>[\w\s]+) AC for account (?<accountNumber>\d+)\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh(?<balance>[\d,]+\.?\d{0,2})"""),
    BUY_GOODS("Buy Goods Till", """(?<transactionId>[A-Z0-9]+) Confirmed\.\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*Ksh(?<amount>[\d,]+\.?\d{0,2}) received from (?<tillNumber>\d+) (?<businessName>[\w\s]+)\.\s*New Account balance is Ksh(?<balance>[\d,]+\.?\d{0,2})"""),
    SEND_MSHWARI("Send money to Mshwari", """(?<transactionId>[A-Z0-9]+) Confirmed\. Ksh(?<amount>[\d,]+\.?\d{0,2}) transferred to M-Shwari account\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\.\s*M-PESA balance is Ksh(?<mpesaBalance>[\d,]+\.?\d{0,2}), new M-Shwari account balance is Ksh(?<mshwariBalance>[\d,]+\.?\d{0,2})"""),
    RECEIVE_MSHWARI("Receive money from Mshwari", """(?<transactionId>[A-Z0-9]+) Confirmed\. You have transferred Ksh(?<amount>[\d,]+\.?\d{0,2}) from your M-Shwari account\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\.\s*M-Shwari balance is Ksh(?<mshwariBalance>[\d,]+\.?\d{0,2})\.\s*M-PESA balance is Ksh(?<mpesaBalance>[\d,]+\.?\d{0,2})"""),
    AIRTIME_PURCHASE("Purchase Airtime", """(?<transactionId>[A-Z0-9]+) confirmed\. You bought Ksh(?<amount>[\d,]+\.?\d{0,2}) of airtime\s*on (?<date>\d{1,2}/\d{1,2}/\d{2,4}) at (?<time>\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh(?<balance>[\d,]+\.?\d{0,2})""");
    //BUNDLES_PURCHASE("Purchase Data Bundles", null);

    fun getRegex(): Regex? {
        return pattern?.toRegex()
    }
}