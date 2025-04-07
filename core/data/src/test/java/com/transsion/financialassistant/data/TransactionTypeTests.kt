package com.transsion.financialassistant.data

import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TransactionTypeTests {
    private lateinit var transactionRepo: TransactionRepo

    @Before
    fun setUp() {
        transactionRepo = TransactionRepoImpl()
    }

    @Test
    fun `should detect SEND_MONEY transaction`() {
        val message =
            "TCH11TSG11 Confirmed. Ksh100.00 sent to BOB  KWENDO 0726245067 on 17/3/25 at 1:36 PM. New M-PESA balance is Ksh1,004.13. Transaction cost, Ksh0.00.  Amount you can transact within the day is 499,680.00. Dial *544*18# & Enjoy 18 min talktime, 180MB & an M-PESA send money transaction all @20 bob."
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.SEND_MONEY)
    }

    @Test
    fun `should detect RECEIVE_MONEY transaction`() {
        val me =
            "TD18YFQ9MW Confirmed.You have received Ksh60,800.00 from KCB 1 501901 on 1/4/25 at 3:52 PM New M-PESA balance is Ksh62,483.13.  Separate personal and business funds through Pochi la Biashara on *334#."
        val message =
            "TCG1XB2SQV Confirmed.You have received Ksh100.00 from WALTER  OUMA 0713497418 on 16/3/25 at 4:55 PM  New M-PESA balance is Ksh69.13. Dial *544*18# & Enjoy 18 min talktime,"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.RECEIVE_MONEY)
    }

    @Test
    fun `should detect PAY_BILL transaction`() {
        val message =
            "TCM0P6D1HI Confirmed. Ksh300.00 sent to Lipa na KCB for account 9000839 on 22/3/25 at 6:22 PM New M-PESA balance is Ksh843.13. Transaction cost, Ksh5.00.Amount you can transact within the day is 499,550.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        //  "TCB48EE7UG Confirmed. Ksh20.00 sent to Equity Paybill Account for account 927001 on 11/3/25 at 8:17 AM New M-PESA balance is Ksh342.05. Transaction cost, Ksh0.00.Amount you can transact within the day is 499,780.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val result = transactionRepo.getTransactionType(message)
        val groups = TransactionType.PAY_BILL.getRegex().find(message)?.groupValues

        groups?.forEachIndexed { index, value ->
            println("$index : $value")
        }

        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.PAY_BILL)
    }

    @Test
    fun `should detect BUY_GOODS transaction`() {
        val message =
            "TCG3YADR1X Confirmed. Ksh2,045.00 paid to POWERMART SUPERMARKET. on 16/3/25 at 7:51 PM.New M-PESA balance is Ksh1,324.13. Transaction cost, Ksh0.00. Amount you can transact within the day is 497,455.00. Save frequent Tills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.BUY_GOODS)
    }

    @Test
    fun `should detect AIRTIME_PURCHASE transaction`() {
        val message =
            "TBC5RBYBPR confirmed.You bought Ksh20.00 of airtime on 12/2/25 at 4:57 PM.New M-PESA balance is Ksh1,685.97. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,660.00. Buy airtime from 1 bob via M-PESA today & keep that conversation going!"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.AIRTIME_PURCHASE)
    }

    @Test
    fun `should detect WITHDRAWAL transaction`() {
        val message =
            "SL14U1AA94 Confirmed.on 1/12/24 at 6:44 PMWithdraw Ksh2,100.00 from 606394 - Estina abshir shop 7street sec ave eastleigh New M-PESA balance is Ksh258.61. Transaction cost, Ksh29.00. Amount you can transact within the day is 496,850.00. To move money from bank to M-PESA, dial *334#>Withdraw>From Bank to MPESA"
        //"SL14U1AA94 Confirmed.on 1/12/24 at 6:44 PMWithdraw Ksh2,100.00 from 606394 - Estina abshir shop 7street sec ave eastleigh New M-PESA balance is Ksh258.61. Transaction cost, Ksh29.00. To move money from bank to M-PESA, dial *334#>Withdraw>From Bank to MPESA"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.WITHDRAWAL)
    }

    @Test
    fun `should detect DEPOSIT transaction`() {
        val message =
            "TA6678JM3W Confirmed. On 6/1/25 at 4:22 PM Give Ksh1,000.00 cash to HASHI COMM Evalast EnterprisesGITHURAI 44 New M-PESA balance is Ksh1,335.89. You can now access M-PESA via *334#"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.DEPOSIT)
    }

    @Test
    fun `should detect SEND_MSHWARI transaction`() {
        val message =
            "TC57GMRGAT Confirmed.Ksh15,000.00 transferred to M-Shwari account on 5/3/25 at 11:53 AM. M-PESA balance is Ksh12,066.05 .New M-Shwari saving account balance is Ksh17,033.79. Transaction cost Ksh.0.00"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.SEND_MSHWARI)
    }

    @Test
    fun `should detect RECEIVE_MSHWARI transaction`() {
        val message =
            "TCP22LYZH8 Confirmed.Ksh20.00 transferred from M-Shwari account on 25/3/25 at 4:41 PM. M-Shwari balance is Ksh10.29 .M-PESA balance is Ksh79.61 .Transaction cost Ksh.0.00"

        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.RECEIVE_MSHWARI)
    }

    @Test
    fun `should detect send pochi transaction`() {
        val message =
            "TCH01GAG8Y Confirmed. Ksh10.00 sent to richard  machage on 17/3/25 at 12:03 PM. New M-PESA balance is Ksh358.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,990.00"
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.SEND_POCHI)
    }

    @Test
    fun `should detect receive pochi transaction`() {
        val message =
            "TCH01GAG8Y Confirmed.You have received Ksh10.00 from RICHARD  MACHAGE on 17/3/25 at 12:03 PM  New business balance is Ksh10.00. To access your funds, Dial *334#,select Pochi la Biashara & Withdraw funds."
        val result = transactionRepo.getTransactionType(message)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.RECEIVE_POCHI)
    }


    @Test
    fun `should handle null input safely`() {
        val message: String? = null
        val result = transactionRepo.getTransactionType(message ?: "")
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.UNKNOWN)
    }
}