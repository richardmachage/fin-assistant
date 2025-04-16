package com.transsion.financialassistant.data.models

enum class TransactionType(val description: String) {
    DEPOSIT("Money Deposit"),
    WITHDRAWAL("Money Withdrawal"),
    SEND_MONEY("Send Money"),
    RECEIVE_MONEY("Receive Money"),
    RECEIVE_POCHI("Receive in Pochi la Biashara"),
    SEND_POCHI("Pay with Pochi"),
    PAY_BILL("Pay Bill"),
    BUY_GOODS("Buy Goods Till"),
    SEND_MSHWARI("Send money to Mshwari"),
    RECEIVE_MSHWARI("Receive money from Mshwari"),
    AIRTIME_PURCHASE("Purchase Airtime"),
    BUNDLES_PURCHASE("Purchase Data & Bundles"),
    MOVE_TO_POCHI("Moved to Pochi Wallet"),
    UNKNOWN("Unknown"),
    ;

    fun getRegex(): Regex {
        return when (this) {
            DEPOSIT -> "(\\b[A-Z0-9]+\\b) Confirmed\\. On (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2}) Give Ksh([\\d,]+\\.?\\d{0,2}) cash to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            WITHDRAWAL -> "(\\b[A-Z0-9]+\\b) Confirmed\\.on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})Withdraw Ksh([\\d,]+\\.?\\d{0,2}) from ([\\d]+ - [A-Za-z0-9 .-]+)New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            SEND_MONEY -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) (\\d{10}) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\.  Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            RECEIVE_MONEY -> "(?:Congratulations!\\s+)?(\\b[A-Z0-9]+\\b)\\s+confirmed\\.\\s*You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9\\s\\p{P}\\p{S}_]+?)(?: (\\d{10}))? on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} (?:AM|PM))\\.?\\s*New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )
            // "(\\b[A-Z0-9]+\\b) Confirmed\\.\\s?You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9 ]+) (\\d+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} (?:AM|PM))\\s+New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?".toRegex()
            RECEIVE_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed\\.You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2}) {2}New business balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?"
                .toRegex(RegexOption.IGNORE_CASE)

            SEND_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?"
                .toRegex()
            //RECEIVE_MONEY_BANK -> """([A-Z0-9]+) Confirmed\.\s*You have received Ksh([\d,]+\.?\d{0,2}) from\s*([\d]+ - [\w\s]+)\s*on (\d{1,2}/\d{1,2}/\d{2,4}) at (\d{1,2}:\d{2} [APM]{2})\s*New M-PESA balance is Ksh([\d,]+\.?\d{0,2})""".toRegex()
            PAY_BILL -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) for account ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2}) New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\.Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            BUY_GOODS -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) paid to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+)\\. on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            SEND_MSHWARI -> "(\\b[A-Z0-9]+\\b) Confirmed\\.Ksh([\\d,]+\\.?\\d{0,2}) transferred to M-Shwari account on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2}) \\..*?New M-Shwari saving account balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost Ksh\\.([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            RECEIVE_MSHWARI -> "(\\b[A-Z0-9]+\\b) Confirmed\\.Ksh([\\d,]+\\.?\\d{0,2}) transferred from M-Shwari account on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. M-Shwari balance is Ksh([\\d,]+\\.?\\d{0,2}) \\.M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2}) \\.Transaction cost Ksh\\.([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )

            AIRTIME_PURCHASE -> "(\\b[A-Z0-9]+\\b) confirmed\\.You bought Ksh([\\d,]+\\.?\\d{0,2}) of airtime(?: for (\\d{9,13}))? on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} (?:AM|PM))\\.New\\s+(?:M-PESA\\s+)?balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(?:\\.(.*))?".toRegex(
                RegexOption.IGNORE_CASE
            )//"(\\b[A-Z0-9]+\\b) confirmed\\.You bought Ksh([\\d,]+\\.?\\d{0,2}) of airtime on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(RegexOption.IGNORE_CASE)
            BUNDLES_PURCHASE -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Z ]+) for account ([A-Z ]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})(.*)?"
                .toRegex(RegexOption.IGNORE_CASE)

            MOVE_TO_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed, Ksh([\\d,]+\\.?\\d{0,2}) has been moved from your M-PESA account to your business account on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.?\\.? New business balance is Ksh([\\d,]+\\.?\\d{0,2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\.".toRegex(
                RegexOption.IGNORE_CASE
            )

            UNKNOWN -> "".toRegex()
        }
    }
}