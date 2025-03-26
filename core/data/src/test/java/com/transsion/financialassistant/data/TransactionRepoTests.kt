package com.transsion.financialassistant.data

import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TransactionRepoTests {
    private lateinit var transactionRepo: TransactionRepo

    @Before
    fun setUp() {
        transactionRepo = TransactionRepoImpl()
    }

    @Test
    fun testSuccessfulParseOfSendMoneyMessage() {
        val message =
            "TCO5V187SF Confirmed. Ksh100.00 sent to FRANCIS  NGIGI 0113184031 on 24/3/25 at 7:02 AM. New M-PESA balance is Ksh2,128.13. Transaction cost, Ksh0.00.  Amount you can transact within the day is 499,900.00. Dial *544*18# & Enjoy 18 min talktime, 180MB & an M-PESA send money transaction all @20 bob."

        val entity = transactionRepo.parseSendMoneyMessage(
            message = message,
            phone = "0718353505"
        )

        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCO5V187SF")
        assertTrue(entity?.sentToName == "FRANCIS  NGIGI")
    }

    @Test
    fun testSuccessfulParseOfReceiveMoneyMessage() {
        val message =
            "TCN1UNF1RZ Confirmed.You have received Ksh1,600.00 from KELVIN  OMUTERE 0759733329 on 23/3/25 at 10:28 PM  New M-PESA balance is Ksh2,228.13. Dial *544*18# & Enjoy 18 min talktime, 180MB & an M-PESA send money transaction all @20 bob."

        val entity = transactionRepo.parseReceiveMoneyMessage(
            message = message,
            phone = "0718353505"
        )

        assertNotNull(entity)
        assertTrue("TCN1UNF1RZ" == entity?.transactionCode)
        assertTrue("KELVIN  OMUTERE" == entity?.receiveFromName)
        assertTrue("0759733329" == entity?.receiveFromPhone)
        assertTrue(1600.00 == entity?.amount)
    }
    @Test
    fun testSuccessfulParseOfPayBillMessage() {
        val message =
            //"TCB48EE7UG Confirmed. Ksh20.00 sent to Joshua Me Paid for account 927001 on 11/3/25 at 8:17 AM New M-PESA balance is Ksh342.05. Transaction cost, Ksh0.00.Amount you can transact within the day is 499,780.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        "TCM0P6D1HI Confirmed. Ksh300.00 sent to Lipa na KCB for account 9000839 on 22/3/25 at 6:22 PM New M-PESA balance is Ksh843.13. Transaction cost, Ksh5.00.Amount you can transact within the day is 499,550.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        val entity = transactionRepo.parsePayBillMessage(
            message = message,
            phone = "0718353505"
        )
        assertNotNull(entity)
        //entity?.let {
            assertTrue(entity?.transactionCode == "TCM0P6D1HI")
            assertTrue(entity?.paidToName == "Lipa na KCB")
            assertTrue(entity?.paidToAccountNo == "9000839")
            assertTrue(entity?.amount == 300.00)
        }


    @Test
    fun testSuccessfulParseOfBuyGoodsMessage() {
        val message =
            "TCM5P70VVL Confirmed. Ksh215.00 paid to MARY NGANGA. on 22/3/25 at 6:25 PM.New M-PESA balance is Ksh628.13. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,335.00. Save frequent Tills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        val entity = transactionRepo.parseBuyGoodsMessage(
            message = message,
            phone = "0718353505"
        )

        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCM5P70VVL")
        assertTrue(entity?.paidTo == "MARY NGANGA")
        assertTrue(entity?.amount == 215.00)
        assertTrue(entity?.transactionCategory == TransactionCategory.OUT)
        assertTrue(entity?.date == "22/3/25")
        assertTrue(entity?.time == "6:25 PM")
        assertTrue(entity?.mpesaBalance == 628.13)
        assertTrue(entity?.transactionCost == 0.00)
    }


    @Test
    fun testSuccessfulParseOfSendPochiMessage() {
        val message =
            "TCO6V8U6WE Confirmed. Ksh20.00 sent to caxton  munyoki on 24/3/25 at 8:14 AM. New M-PESA balance is Ksh2,108.13. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,880.00. Sign up for Lipa Na M-PESA Till online https://m-pesaforbusiness.co.ke"

        val entity = transactionRepo.parseSendPochiMessage(
            message = message,
            phone = "0718353505"
        )

        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCO6V8U6WE")
        assertTrue(entity?.sentToName == "caxton  munyoki")
        assertTrue(entity?.amount == 20.00)
        assertTrue(entity?.mpesaBalance == 2108.13)
        assertTrue(entity?.transactionCost == 0.00)
        assertTrue(entity?.date == "24/3/25")
        assertTrue(entity?.time == "8:14 AM")
        assertTrue(entity?.transactionCategory == TransactionCategory.OUT)
    }

    @Test
    fun testSuccessfulParseOfDepositMoneyMessage() {
        val message =
            "TA6678JM3W Confirmed. On 6/1/25 at 4:22 PM Give Ksh1,000.00 cash to HASHI COMM Evalast EnterprisesGITHURAI 44 New M-PESA balance is Ksh1,335.89. You can now access M-PESA via *334#"

        val entity = transactionRepo.parseDepositMoneyMessage(
            message = message,
            phone = "0718353505"
        )
        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TA6678JM3W")
        assertTrue(entity?.date == "6/1/25")
        assertTrue(entity?.amount == 1000.00)
        assertTrue(entity?.agentDepositedTo == "HASHI COMM Evalast EnterprisesGITHURAI 44")
        assertTrue(entity?.mpesaBalance == 1335.89)
        assertTrue(entity?.transactionCategory == TransactionCategory.IN)
    }

    @Test
    fun testSuccessfulParseOfReceivePochiMessage() {
        val message =
            //"TCP124XF3T Confirmed.You have received Ksh1.00 from ALEX  MWANGI on 25/3/25 at 2:46 PM  New business balance is Ksh1.25. To access your funds, Dial *334#,select Pochi la Biashara & Withdraw funds."
            "TCH01GAG8Y Confirmed.You have received Ksh10.00 from RICHARD  MACHAGE on 17/3/25 at 12:03 PM  New business balance is Ksh10.00. To access your funds, Dial *334#,select Pochi la Biashara & Withdraw funds."

        val entity = transactionRepo.parseReceivePochiMessage(
            message = message,
            phone = "0718353505"
        )

        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCH01GAG8Y")
        assertTrue(entity?.amount == 10.00)
        assertTrue(entity?.receiveFromName == "RICHARD  MACHAGE")
    }

    @Test
    fun testSuccessfulParseOfBundlesPurchaseMessage() {
        val message =
            "TCK6F5UHUO Confirmed. Ksh30.00 sent to SAFARICOM DATA BUNDLES for account SAFARICOM DATA BUNDLES on 20/3/25 at 2:25 PM. New M-PESA balance is Ksh96.61. Transaction cost, Ksh0.00."
        val entity = transactionRepo.parseBundlesPurchaseMessage(
            message = message,
            phone = "0718353505"
        )
        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCK6F5UHUO")
        assertTrue(entity?.accountNumber == "SAFARICOM DATA BUNDLES")
        assertTrue(entity?.accountName == "SAFARICOM DATA BUNDLES")
        assertTrue(entity?.amount == 30.00)
    }

    @Test
    fun testSuccessfulParseofReceiveMshwariMessage() {
        val message =
            "TCP22LYZH8 Confirmed.Ksh20.00 transferred from M-Shwari account on 25/3/25 at 4:41 PM. M-Shwari balance is Ksh10.29 .M-PESA balance is Ksh79.61 .Transaction cost Ksh.0.00"

        val entity = transactionRepo.parseReceiveMshwariMessage(
            message = message,
            phone = "0718353505"
        )
//        assertTrue(entity != null)
//        assertTrue(entity?.transactionCode == "TCP22LYZH8")
//        assertTrue(entity?.account == "M-Shwari account")
//        assertTrue(entity?.mpesaBalance ==  20.00)
//        assertTrue(entity?.mshwariBalance == 79.29)
//        assertTrue(entity?.amount == 10.29)
    }

    @Test
    fun testSuccessfulParseOfSendMshwariMessage() {
        val message =
            "TCP82LUXUW Confirmed.Ksh30.00 transferred to M-Shwari account on 25/3/25 at 4:41 PM. M-PESA balance is Ksh59.61 .New M-Shwari saving account balance is Ksh30.29. Transaction cost Ksh.0.00"

        val entity = transactionRepo.parseSendMshwariMessage(
            message = message,
            phone = "0718353505"
        )
        assertTrue(entity != null)
        assertTrue(entity?.transactionCode == "TCP82LUXUW")
        assertTrue(entity?.mpesaBalance == 59.61)
        assertTrue(entity?.mshwariBalance == 30.29)
        assertTrue(entity?.amount == 30.00)
    }

    @Test
    fun testSuccessfulParseOfPurchaseAirtimeMessage() {
        val message =
            "TCA95DZ8Q7 confirmed.You bought Ksh30.00 of airtime on 10/3/25 at 2:27 PM.New M-PESA balance is Ksh43.61. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,890.00. Start Investing today with Ziidi MMF & earn daily. Dial *334#"

        val entity = transactionRepo.parsePurchaseAirtimeMessage(
            message = message,
            phone = "0718353505"
        )

//        assertTrue(entity != null)
//        assertTrue(entity?.transactionCode == "TCA95DZ8Q7")
//        assertTrue(entity?.amount == 30.00)
//        assertTrue(entity?.mpesaBalance == 43.61)
//        assertTrue(entity?.date == "10/3/25")
//        assertTrue(entity?.time == "2:27 PM")
    }

    @Test
    fun testSuccessfulParseofWithdrawalMessage() {
        val message =
            ""

    }

}