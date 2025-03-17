package com.transsion.financialassistant.background

/**
 * This function is built on top of the [getMpesaMessages] function.
 *
 */
/*
fun getMpesaMessagesByTransactionType(
    transactionType: TransactionType,
    context: Context,
    getExecutionTime: (Duration) -> Unit = {}
): List<MpesaMessage> {

    return when (transactionType) {
        TransactionType.BUNDLES_PURCHASE -> {
            */
/** Ensure always, this is the first condition
             * because the buy bundles and PayBill messages overlap
 * such that each contains "for account part*//*

            getMpesaMessages(
                filterValue = "BUNDLES",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }
            )
        }

        TransactionType.AIRTIME_PURCHASE -> {
            getMpesaMessages(
                filterValue = "You bought",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                },

                )
        }

        TransactionType.DEPOSIT -> {
            getMpesaMessages(
                filterValue = "Give",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.WITHDRAWAL -> {
            getMpesaMessages(
                filterValue = "Withdraw",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.SEND_MONEY -> {
            getMpesaMessages(
                filterValue = "sent",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }
            )
        }

        TransactionType.RECEIVE_MONEY -> {
            getMpesaMessages(
                filterValue = "received",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.RECEIVE_POCHI -> {
            getMpesaMessages(
                filterValue = "POCHI",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.PAY_BILL -> {
            getMpesaMessages(
                filterValue = "for account",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.BUY_GOODS -> {
            getMpesaMessages(
                filterValue = "paid to",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.SEND_MSHWARI -> {
            getMpesaMessages(
                filterValue = "transferred to M-Shwari",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.RECEIVE_MSHWARI -> {
            getMpesaMessages(
                filterValue = "from your M-Shwari",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                    getExecutionTime(it)
                }

            )
        }

        TransactionType.UNKNOWN -> {
            //FIXME
            emptyList()
        }

        TransactionType.SEND_POCHI -> {
            getMpesaMessages(
                filterValue = "sent to richard",
                context = context,
                getExecutionTime = {
                    Log.d("DurationInFilter", "Time taken: $it")
                }
            )

        }
    }


}*/
