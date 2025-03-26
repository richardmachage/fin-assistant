package com.transsion.financialassistant.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepo
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepoImpl
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepo
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepoImpl
import com.transsion.financialassistant.data.room.db.FinancialAssistantDb
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDbTests {

    private lateinit var db: FinancialAssistantDb
    private lateinit var sendMoneyRepo: SendMoneyRepo
    private lateinit var receiveMoneyRepo: ReceiveMoneyRepo
    private lateinit var buyGoodsRepo: BuyGoodsRepo
    private lateinit var payBillRepo: PayBillRepo
    private lateinit var appContext: Context

    @Before
    fun setUp() {
        appContext = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context = appContext,
            klass = FinancialAssistantDb::class.java
        )
            .allowMainThreadQueries()
            .build()


        sendMoneyRepo = SendMoneyRepoImpl(
            sendMoneyDao = db.sendMoneyDao()
        )
        receiveMoneyRepo = ReceiveMoneyRepoImpl(receiveMoneyDao = db.receiveMoneyDao())
        payBillRepo = PayBillRepoImpl(payBillDao = db.payBillDao())
        buyGoodsRepo = BuyGoodsRepoImpl(buyGoodsDao = db.buyGoodsDao())

    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertSendMoneyTransaction() = runTest {
        val message =
            "TCH11TSG11 Confirmed. Ksh100.00 sent to BOB  KWENDO 0726245067 on 17/3/25 at 1:36 PM. New M-PESA balance is Ksh1,004.13. Transaction cost, Ksh0.00.  Amount you can transact within the day is 499,680.00. Dial *544*18# & Enjoy 18 min talktime, 180MB & an M-PESA send money transaction all @20 bob."
        var successCalled = false

        sendMoneyRepo.insertSendMoneyTransaction(
            message = message,
            context = appContext,
            subId = 1,
            onSuccess = {
                successCalled = true
            },
            onFailure = {
                fail(it)
            },
            phone = "88735"
        )
        assertTrue(successCalled)
    }

    @Test
    fun testInsertReceiveMoneyTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCG1XB2SQV Confirmed.You have received Ksh100.00 from WALTER  OUMA 0713497418 on 16/3/25 at 4:55 PM  New M-PESA balance is Ksh69.13. Dial *544*18# & Enjoy 18 min talktime,"

        var successCalled = false

        receiveMoneyRepo.insertReceiveMoneyTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098765",
            onSuccess = { successCalled = true },
            onFailure = { fail(it) }
        )

        assertTrue(successCalled)
    }


    @Test
    fun testInsertBuyGoodsTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCG3YADR1X Confirmed. Ksh2,045.00 paid to POWERMART SUPERMARKET. on 16/3/25 at 7:51 PM.New M-PESA balance is Ksh1,324.13. Transaction cost, Ksh0.00. Amount you can transact within the day is 497,455.00. Save frequent Tills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        var successCalled = false

        buyGoodsRepo.insertBuyGoodsTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098765",
            onSuccess = { successCalled = true },
            onFailure = { fail(it) }
        )

        assertTrue(successCalled)
    }

    @Test
    fun testInsertPayBillTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCB48EE7UG Confirmed. Ksh20.00 sent to Equity Paybill Account for account 927001 on 11/3/25 at 8:17 AM New M-PESA balance is Ksh342.05. Transaction cost, Ksh0.00.Amount you can transact within the day is 499,780.00. Save frequent paybills for quick payment on M-PESA app https://bit.ly/mpesalnk"

        var successCalled = false

        payBillRepo.insertPayBillTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098765",
            onSuccess = { successCalled = true },
            onFailure = { fail(it) }
        )

        assertTrue(successCalled)
    }



}