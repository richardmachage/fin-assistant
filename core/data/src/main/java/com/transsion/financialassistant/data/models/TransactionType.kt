package com.transsion.financialassistant.data.models

enum class TransactionType(val description: String) {
    DEPOSIT("Deposit Money"),
    WITHDRAWAL("Money Withdrawal"),
    SEND_MONEY("Send Money"),
    RECEIVE_MONEY("Received Money"),
    RECEIVE_POCHI("Received in Pochi la Biashara"),
    SEND_POCHI("Paid to Pochi"),
    PAY_BILL("Pay Bill"),
    BUY_GOODS("Buy Goods Till"),
    SEND_MSHWARI("Send money to Mshwari"),
    RECEIVE_MSHWARI("Received money from Mshwari"),
    AIRTIME_PURCHASE("Purchase Airtime"),
    BUNDLES_PURCHASE("Purchase Data & Bundles"),
    MOVE_TO_POCHI("Moved to Pochi Wallet"),
    MOVE_FROM_POCHI("Moved from Pochi to M-PESA"),
    SEND_MONEY_FROM_POCHI("Send Money from Pochi Wallet"),
    FULIZA_PAY("Paid Fuliza"),
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

            SEND_MONEY -> "(?<txnId1>\\b[A-Z0-9]+\\b) Confirmed\\. Ksh(?<amount1>[\\d,]+\\.?\\d{0,2}) sent to (?<recipient1>[A-Za-z0-9\\s\\p{P}\\p{S}_]+) (?<phone1>\\d{10}) on (?<date1>\\d{1,2}/\\d{1,2}/\\d{2}) at (?<time1>\\d{1,2}:\\d{2} [APM]{2})\\. New M-PESA balance is Ksh(?<balance1>[\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh(?<cost1>[\\d,]+\\.?\\d{0,2})\\.\\s*Amount you can transact within the day is (?<limit1>[\\d,]+\\.?\\d{0,2})(?<trailing1>.*)?|(?<txnId2>\\b[A-Z0-9]+\\b) Confirmed\\. You have sent Ksh(?<amount2>[\\d,]+\\.?\\d{0,2}) to (?<recipient2>[A-Za-z\\s]+) on (?<date2>\\d{2}/\\d{2}/\\d{4})\\s+at (?<time2>\\d{1,2}:\\d{2} [AP]M)\\. New MPESA balance is Ksh(?<balance2>[\\d,]+\\.?\\d{0,2})\\.".toRegex(
                RegexOption.IGNORE_CASE
            )
            //"(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) (\\d{10}) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\.  Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?|(\\b[A-Z0-9]+\\b) Confirmed\\. You have sent Ksh([\\d,]+\\.?\\d{0,2}) to ([A-Za-z\\s]+) on (\\d{2}/\\d{2}/\\d{4})\\s+at (\\d{1,2}:\\d{2} [AP]M)\\. New MPESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\.".toRegex(RegexOption.IGNORE_CASE)

            SEND_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed\\. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z'\\s]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.? New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\.\\s*Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(.*)?"
                .toRegex(RegexOption.IGNORE_CASE)


            RECEIVE_MONEY -> "(?:Congratulations!\\s+)?(\\b[A-Z0-9]+\\b)\\s+confirmed\\.\\s*You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9\\s\\p{P}\\p{S}_]+?)(?: (\\d{10}))? on (\\d{1,2}/\\d{1,2}/\\d{2,4}) at (\\d{1,2}:\\d{2} (?:AM|PM))\\.?\\s*New M-?PESA balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?".toRegex(
                RegexOption.IGNORE_CASE
            )
            // "(\\b[A-Z0-9]+\\b) Confirmed\\.\\s?You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9 ]+) (\\d+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} (?:AM|PM))\\s+New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?".toRegex()
            RECEIVE_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed\\.You have received Ksh([\\d,]+\\.?\\d{0,2}) from ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2}) {2}New business balance is Ksh([\\d,]+\\.?\\d{0,2})(.*)?"
                .toRegex(RegexOption.IGNORE_CASE)

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

            MOVE_TO_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed, Ksh([\\d,]+\\.?\\d{0,2}) has been moved from your M-PESA account to your business account on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.?\\.? New business balance is Ksh([\\d,]+\\.?\\d{0,2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})(?:\\.(.*))?".toRegex(
                RegexOption.IGNORE_CASE
            )

            MOVE_FROM_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed, Ksh([\\d,]+\\.?\\d{0,2}) has been moved from your business account to your M-PESA account on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.?\\.? New business balance is Ksh([\\d,]+\\.?\\d{0,2})\\. New M-PESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})(?:\\.(.*))?".toRegex(
                RegexOption.IGNORE_CASE
            )

            SEND_MONEY_FROM_POCHI -> "(\\b[A-Z0-9]+\\b) Confirmed. Ksh([\\d,]+\\.?\\d{0,2}) sent to ([A-Za-z0-9\\s\\p{P}\\p{S}_]+) on (\\d{1,2}/\\d{1,2}/\\d{2}) at (\\d{1,2}:\\d{2} [APM]{2})\\.? New business balance is Ksh([\\d,]+\\.?\\d{0,2})\\. Transaction cost, Ksh([\\d,]+\\.?\\d{0,2})\\. Amount you can transact within the day is ([\\d,]+\\.?\\d{0,2})(?:\\.(.*))?".toRegex(
                RegexOption.IGNORE_CASE
            )

            FULIZA_PAY -> FULIZA_CUT_REGEX


            UNKNOWN -> "".toRegex()
        }
    }
}


val FULIZA_CUT_REGEX = (
        "(\\b[A-Z0-9]+\\b)\\s+Confirmed\\.\\s*Ksh\\s*([\\d,]+\\.?\\d{0,2}) from your M-PESA has been used to fully pay your outstanding Fuliza M-PESA\\.\\s*" +
                "Available Fuliza M-PESA limit is Ksh\\s*([\\d,]+\\.?\\d{0,2})\\.\\s*M-PESA balance is Ksh\\s*([\\d,]+\\.?\\d{0,2})\\."
        ).toRegex(RegexOption.IGNORE_CASE)


val bridgeRegex =
    "(\\b[A-Z0-9]+\\b) Confirmed\\. You have sent Ksh([\\d,]+\\.?\\d{0,2}) to ([A-Za-z\\s]+) on (\\d{2}/\\d{2}/\\d{4})\\s+at (\\d{1,2}:\\d{2} [AP]M)\\. New MPESA balance is Ksh([\\d,]+\\.?\\d{0,2})\\.".toRegex()


