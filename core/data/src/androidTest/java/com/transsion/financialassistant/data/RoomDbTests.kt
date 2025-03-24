package com.transsion.financialassistant.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.transsion.financialassistant.data.repository.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.send_money.SendMoneyRepoImpl
import com.transsion.financialassistant.data.room.db.FinancialAssistantDb
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDbTests {

    private lateinit var db: FinancialAssistantDb
    private lateinit var sendMoneyRepo: SendMoneyRepo

    @Before
    fun steUpDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = FinancialAssistantDb.getInstance(appContext)

        sendMoneyRepo = SendMoneyRepoImpl(
            sendMoneyDao = db.sendMoneyDao()
        )
    }


}