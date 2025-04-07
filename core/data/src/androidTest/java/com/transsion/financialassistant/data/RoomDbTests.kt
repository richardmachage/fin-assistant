package com.transsion.financialassistant.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepo
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepoImpl
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepo
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepoImpl
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepo
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepoImpl
import com.transsion.financialassistant.data.repository.transaction.deposit.DepositRepo
import com.transsion.financialassistant.data.repository.transaction.deposit.DepositRepoImpl
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepo
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_pochi.ReceivePochiRepo
import com.transsion.financialassistant.data.repository.transaction.receive_pochi.ReceivePochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_pochi.SendPochiRepo
import com.transsion.financialassistant.data.repository.transaction.send_pochi.SendPochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepoImpl
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
    private lateinit var withdrawMoneyRepo: WithdrawMoneyRepo
    private lateinit var buyAirtimeRepo: BuyAirtimeRepo
    private lateinit var sendMshwariRepo: SendMshwariRepo
    private lateinit var receiveMshwariRepo: ReceiveMshwariRepo
    private lateinit var bundlesPurchaseRepo: BundlesPurchaseRepo
    private lateinit var appContext: Context
    private lateinit var sendPochiRepo: SendPochiRepo
    private lateinit var receivePochiRepo: ReceivePochiRepo
    private lateinit var depositRepo: DepositRepo

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
        withdrawMoneyRepo = WithdrawMoneyRepoImpl(withdrawMoneyDao = db.withdrawMoneyDao())
        buyAirtimeRepo = BuyAirtimeRepoImpl(buyAirtimeDao = db.buyAirtimeDao())
        sendMshwariRepo = SendMshwariRepoImpl(sendMshwariDao = db.sendMshwariDao())
        receiveMshwariRepo = ReceiveMshwariRepoImpl(receiveMshwariDao = db.receiveMshwariDao())
        bundlesPurchaseRepo = BundlesPurchaseRepoImpl(bundlesPurchaseDao = db.bundlesPurchaseDao())
        sendPochiRepo = SendPochiRepoImpl(sendPochiDao = db.sendPochiDao())
        receivePochiRepo = ReceivePochiRepoImpl(receivePochiDao = db.receivePochiDao())
        depositRepo = DepositRepoImpl(depositMoneyDao = db.depositMoneyDao())

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

    @Test
    fun testInsertWithdrawMoneyTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "SL14U1AA94 Confirmed.on 1/12/24 at 6:44 PMWithdraw Ksh2,100.00 from 606394 - Estina abshir shop 7street sec ave eastleigh New M-PESA balance is Ksh258.61. Transaction cost, Ksh29.00. Amount you can transact within the day is 496,850.00. To move money from bank to M-PESA, dial *334#>Withdraw>From Bank to MPESA"
        var successCalled = false

        withdrawMoneyRepo.insertWithdrawMoneyTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098765",
            onSuccess = { successCalled = true },
            onFailure = { fail(it) }
        )

        // testing select withdraw transaction by date
        withdrawMoneyRepo.getWithdrawMoneyTransactionsByDate(
            startDate = "30/11/24",
            endDate = "2/12/24",
            onSuccess = { successCalled = true },
            onFailure = { fail(it) }
        )

        assertTrue(successCalled)
    }

    @Test
    fun testInsertBuyAirtimeTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCA95DZ8Q7 confirmed.You bought Ksh30.00 of airtime on 10/3/25 at 2:27 PM.New M-PESA balance is Ksh43.61. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,890.00. Start Investing today with Ziidi MMF & earn daily. Dial *334#"

        var successCalled = false

        buyAirtimeRepo.insertBuyAirtimeTransaction(
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
    fun testInsertSendMshwariTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCP82LUXUW Confirmed.Ksh30.00 transferred to M-Shwari account on 25/3/25 at 4:41 PM. M-PESA balance is Ksh59.61 .New M-Shwari saving account balance is Ksh30.29. Transaction cost Ksh.0.00"

        var successCalled = false

        sendMshwariRepo.insertSendMshwariTransaction(
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
    fun testInsertReceiveMshwariTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCP22LYZH8 Confirmed.Ksh20.00 transferred from M-Shwari account on 25/3/25 at 4:41 PM. M-Shwari balance is Ksh10.29 .M-PESA balance is Ksh79.61 .Transaction cost Ksh.0.00"

        var successCalled = false

        receiveMshwariRepo.insertReceiveMshwariTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098765",
            onSuccess = {
                println("Transaction inserted successfully!")
                successCalled = true
            },
            onFailure = {
                println("Transaction insertion failed!")
                fail(it)
            }
        )

        assertTrue(successCalled)
    }

    @Test
    fun testBundlesPurchaseTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCK6F5UHUO Confirmed. Ksh30.00 sent to SAFARICOM DATA BUNDLES for account SAFARICOM DATA BUNDLES on 20/3/25 at 2:25 PM. New M-PESA balance is Ksh96.61. Transaction cost, Ksh0.00."

        var successCalled = false

        bundlesPurchaseRepo.insertBundlesPurchaseTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098769",
            onSuccess = {
                println("Transaction inserted successfully!")
                successCalled = true
            },
            onFailure = {
                println("Transaction insertion failed!")
                fail(it)
            }
        )

        assertTrue(successCalled)
    }

    @Test
    fun testSendPochiTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCH01GAG8Y Confirmed. Ksh10.00 sent to richard  machage on 17/3/25 at 12:03 PM. New M-PESA balance is Ksh358.00. Transaction cost, Ksh0.00. Amount you can transact within the day is 499,990.00"

        var successCalled = false
        sendPochiRepo.insertSendPochiTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098769",
            onSuccess = {
                println("Transaction inserted successfully!")
                successCalled = true
            },
            onFailure = {
                println("Transaction insertion failed!")
                fail(it)
            }
        )

        assertTrue(successCalled)
    }

    @Test
    fun testReceivePochiTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TCH01GAG8Y Confirmed.You have received Ksh10.00 from RICHARD  MACHAGE on 17/3/25 at 12:03 PM  New business balance is Ksh10.00. To access your funds, Dial *334#,select Pochi la Biashara & Withdraw funds."

        var successCalled = false

        receivePochiRepo.insertReceivePochiTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098769",
            onSuccess = {
                println("Transaction inserted successfully!")
                successCalled = true
            },
            onFailure = {
                println("Transaction insertion failed!")
                fail(it)
            }
        )
        assertTrue(successCalled)
    }

    @Test
    fun testDepositTransaction_withPhone_shouldSucceed() = runTest {
        val message =
            "TA6678JM3W Confirmed. On 6/1/25 at 4:22 PM Give Ksh1,000.00 cash to HASHI COMM Evalast EnterprisesGITHURAI 44 New M-PESA balance is Ksh1,335.89. You can now access M-PESA via *334#"
        var successCalled = false

        depositRepo.insertDepositTransaction(
            message = message,
            context = appContext,
            subId = 1,
            phone = "098769",
            onSuccess = {
                println("Transaction inserted Successfully")
                successCalled = true
            },

            onFailure = {
                println("Transaction insertion failed!")
                fail(it)
            }
        )
        assertTrue(successCalled)
    }
}
