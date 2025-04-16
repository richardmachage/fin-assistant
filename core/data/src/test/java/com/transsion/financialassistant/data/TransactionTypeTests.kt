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

        val anci =
            "TDD6KGR4H2 Confirmed. Ksh2,000.00 sent to ANCI-LLAH  CH*ACHA 0711360814 on 13/4/25 at 11:51 AM. New M-PESA balance is Ksh1,354.13. Transaction cost, Ksh33.00.  Amount you can transact within the day is 498,000.00. "
        val thi =
            "TDE1NTEGQX Confirmed. Ksh100.00 sent to MICHAEL NJUKI 0724354182 on 14/4/25 at 7:06 AM. New M-PESA balance is Ksh144.13. Transaction cost, Ksh0.00.  Amount you can transact within the day is 499,900.00. "
        val message =
            "TCH11TSG11 Confirmed. Ksh100.00 sent to BOB  KWENDO 0726245067 on 17/3/25 at 1:36 PM. New M-PESA balance is Ksh1,004.13. Transaction cost, Ksh0.00.  Amount you can transact within the day is 499,680.00. Dial *544*18# & Enjoy 18 min talktime, 180MB & an M-PESA send money transaction all @20 bob."

        val groups = TransactionType.SEND_MONEY.getRegex().find(anci)?.groupValues
        groups?.forEachIndexed { index, value ->
            println("$index : $value")
        }
        val result = transactionRepo.getTransactionType(anci)
        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.SEND_MONEY)
    }

    @Test
    fun `should detect RECEIVE_MONEY transaction`() {
        val ncbaMessage =
            "Congratulations! TD66N8PLHM confirmed.You have received Ksh15,950.00 from LOOP B2C. on 6/4/25 at 2:35 PM.New M-PESA balance is Ksh16,785.29. Separate personal and business funds through Pochi la Biashara on *334#."

        val kcbMessage =
            "TD18YFQ9MW Confirmed.You have received Ksh60,800.00 from KCB 1 501901 on 1/4/25 at 3:52 PM New M-PESA balance is Ksh62,483.13.  Separate personal and business funds through Pochi la Biashara on *334#."
        val message =
            "TCG1XB2SQV Confirmed.You have received Ksh100.00 from WALTER  OUMA 0713497418 on 16/3/25 at 4:55 PM  New M-PESA balance is Ksh69.13. Dial *544*18# & Enjoy 18 min talktime,"
        val result = transactionRepo.getTransactionType(ncbaMessage)
        println("Transaction type: ${result.description}")
        val groups = TransactionType.RECEIVE_MONEY.getRegex().find(ncbaMessage)?.groupValues
        groups?.forEachIndexed { index, value ->
            println("$index : $value")
        }
        assertTrue(result == TransactionType.RECEIVE_MONEY)
    }

    @Test
    fun `should detect PAY_BILL transaction`() {

        val easyCoach =
            "TD943EF52A Confirmed. Ksh2,850.00 sent to EASY COACH RAILWAYS-NRB(HQ) 6 for account 816492 on 9/4/25 at 6:14 PM New M-PESA balance is Ksh4,445.13. Transaction cost, Ksh25.00.Amount you can transact within the day is 496,620.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val coop =
            "TCT8KFQMR8 Confirmed. Ksh6,000.00 sent to Co-operative Bank Money Transfer for account 836993 on 29/3/25 at 2:53 PM New M-PESA balance is Ksh1,631.13. Transaction cost, Ksh42.00.Amount you can transact within the day is 493,850.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val global =
            "TC397N1PP3 Confirmed. Ksh1,100.00 sent to M-PESA  GLOBAL for account 255754992504 on 3/3/25 at 2:11 PM New M-PESA balance is Ksh8,096.05. Transaction cost, Ksh0.00.Amount you can transact within the day is 430,170.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val me2 =
            "TD15YG1YNH Confirmed. Ksh20,000.00 sent to MALI for account Wealth Management on 1/4/25 at 3:54 PM New M-PESA balance is Ksh12,483.13. Transaction cost, Ksh0.00.Amount you can transact within the day is 449,680.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        val message =
            "TCM0P6D1HI Confirmed. Ksh300.00 sent to Lipa na KCB for account 9000839 on 22/3/25 at 6:22 PM New M-PESA balance is Ksh843.13. Transaction cost, Ksh5.00.Amount you can transact within the day is 499,550.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        //  "TCB48EE7UG Confirmed. Ksh20.00 sent to Equity Paybill Account for account 927001 on 11/3/25 at 8:17 AM New M-PESA balance is Ksh342.05. Transaction cost, Ksh0.00.Amount you can transact within the day is 499,780.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val result = transactionRepo.getTransactionType(coop)
        val groups = TransactionType.PAY_BILL.getRegex().find(coop)?.groupValues

        groups?.forEachIndexed { index, value ->
            println("$index : $value")
        }

        println("Transaction type: ${result.description}")
        assertTrue(result == TransactionType.PAY_BILL)
    }

    @Test
    fun `should detect BUY_GOODS transaction`() {
        val eco =
            "TBP9D0EL9H Confirmed. Ksh80.00 paid to ECOLOGICAL DESTINATIONS SAFARIS LIMITED-426H. on 25/2/25 at 7:13 AM.New M-PESA balance is Ksh937.05. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,920.00. Download new M-PESA app on http://bit.ly/mpesappsm & get 500MB FREE data."
        val mm ="TDA65K5PRY Confirmed. Ksh50.00 paid to FANAKA MERCHANTS LIMITED 929. on 10/4/25 at 8:33 AM.New M-PESA balance is Ksh53.61. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,950.00. Save frequent Tills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val message =
            "TCG3YADR1X Confirmed. Ksh2,045.00 paid to POWERMART SUPERMARKET. on 16/3/25 at 7:51 PM.New M-PESA balance is Ksh1,324.13. Transaction cost, Ksh0.00. Amount you can transact within the day is 497,455.00. Save frequent Tills for quick payment on M-PESA app https://bit.ly/mpesalnk"
        val result = transactionRepo.getTransactionType(eco)
        println("Transaction type: ${result.description}")

        assertTrue(result == TransactionType.BUY_GOODS)
    }

    @Test
    fun `should detect AIRTIME_PURCHASE transaction`() {
        val mess =
            "SIU9GEQ7IX confirmed.You bought Ksh70.00 of airtime for 254796487729 on 30/9/24 at 10:01 PM.New  balance is Ksh2,024.74. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,865.00.You can now access M-PESA via *334#"
        val message =
            "TBC5RBYBPR confirmed.You bought Ksh20.00 of airtime on 12/2/25 at 4:57 PM.New M-PESA balance is Ksh1,685.97. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,660.00. Buy airtime from 1 bob via M-PESA today & keep that conversation going!"
        val result = transactionRepo.getTransactionType(message)
        val groups = TransactionType.AIRTIME_PURCHASE.getRegex().find(message)?.groupValues
        groups?.forEachIndexed { index, value ->
            println("$index : $value")
        }
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
            "TCH01GAG8Y Confirmed. Ksh10.00 sent to richard machage on 17/3/25 at 12:03 PM. New M-PESA balance is Ksh358.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,990.00"
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


val sendToZiidi =
    "SLV9I5W4EV Confirmed. Ksh2,000.00 sent to ZIIDI on 31/12/24 at 6:34 PM New M-PESA balance is Ksh2,349.89. Transaction cost, Ksh0.00.Amount you can transact within the day is 498,000.00. Pay your water/KPLC bill conveniently using M-PESA APP or use Paybill option on Lipa Na M-PESA."
val fulizaCut =
    "SKT7KAQMRH Confirmed. Ksh 241.19 from your M-PESA has been used to fully pay your outstanding Fuliza M-PESA. Available Fuliza M-PESA limit is Ksh 700.00. M-PESA balance is Ksh558.81."
val fulizaAmount =
    "SKT9JYXWRJ Confirmed. Fuliza M-PESA amount is Ksh 238.80. Interest charged Ksh 2.39. Total Fuliza M-PESA outstanding amount is Ksh 241.19 due on 29/12/24. To check daily charges, Dial *234*0#OK Select Query Charges"
val reversal =
    "SJP5C9HAIJ confirmed. Reversal of transaction SJH5DP029L has been successfully reversed  on 25/10/24  at 2:04 PM and Ksh1.00 is credited to your M-PESA account. New M-PESA account balance is Ksh1,778.12."
val dataBundlesEmptyAccoountNumer =
    "SGK99ETMW3 Confirmed. Ksh100.00 sent to SAFARICOM DATA BUNDLES for account on 20/7/24 at 11:04 AM. New M-PESA balance is Ksh1,407.98."


val sendFromPochi =
    "TBI5I8EXAH Confirmed, Ksh1,000.00 has been moved from your business account to your M-PESA account on 18/2/25 at 12:12 PM.. New business balance is Ksh0.00. New M-PESA balance is Ksh1,168.18. Transaction cost, Ksh0.00."