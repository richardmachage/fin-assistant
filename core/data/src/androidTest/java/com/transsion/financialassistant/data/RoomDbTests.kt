package com.transsion.financialassistant.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    private lateinit var appContext: Context

    @Before
    fun steUpDb() {
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
            }
        )

        assertTrue(successCalled)


    }


}