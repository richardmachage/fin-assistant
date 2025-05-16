package com.transsion.financialassistant.data.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseDao
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseEntity
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeDao
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeEntity
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsDao
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsEntity
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyDao
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyEntity
import com.transsion.financialassistant.data.room.entities.fuliza_pay.FulizaPayDao
import com.transsion.financialassistant.data.room.entities.fuliza_pay.FulizaPayEntity
import com.transsion.financialassistant.data.room.entities.move_from_pochi.MoveFromPochiDao
import com.transsion.financialassistant.data.room.entities.move_from_pochi.MoveFromPochiEntity
import com.transsion.financialassistant.data.room.entities.move_to_pochi.MoveToPochiDao
import com.transsion.financialassistant.data.room.entities.move_to_pochi.MoveToPochiEntity
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillDao
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillEntity
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyDao
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariDao
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariEntity
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiDao
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiEntity
import com.transsion.financialassistant.data.room.entities.send_from_pochi.SendFromPochiDao
import com.transsion.financialassistant.data.room.entities.send_from_pochi.SendFromPochiEntity
import com.transsion.financialassistant.data.room.entities.send_global.SendGlobalDao
import com.transsion.financialassistant.data.room.entities.send_global.SendGlobalEntity
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyDao
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariDao
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariEntity
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiDao
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiEntity
import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntity
import com.transsion.financialassistant.data.room.entities.unknown.UnknownEntityDao
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyDao
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyEntity
import com.transsion.financialassistant.data.room.views.business.UnifiedIncomingTransactionsBusiness
import com.transsion.financialassistant.data.room.views.business.UnifiedOutGoingTransactionsBusiness
import com.transsion.financialassistant.data.room.views.business.UnifiedTransactionBusiness
import com.transsion.financialassistant.data.room.views.business.UnifiedTransactionsBusinessDao
import com.transsion.financialassistant.data.room.views.personal.UnifiedIncomingTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedOutGoingTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionsPersonalDao

@Database(
    entities = [
        DepositMoneyEntity::class,
        ReceiveMoneyEntity::class,
        SendPochiEntity::class,
        WithdrawMoneyEntity::class,
        BuyGoodsEntity::class,
        PayBillEntity::class,
        SendMoneyEntity::class,
        BuyAirtimeEntity::class,
        SendMshwariEntity::class,
        ReceiveMshwariEntity::class,
        SendGlobalEntity::class,
        BundlesPurchaseEntity::class,
        ReceivePochiEntity::class,
        MoveToPochiEntity::class,
        MoveFromPochiEntity::class,
        SendFromPochiEntity::class,
        FulizaPayEntity::class,
        UnknownEntity::class
    ],
    views = [
        UnifiedIncomingTransaction::class,
        UnifiedOutGoingTransaction::class,
        UnifiedTransactionPersonal::class,
        UnifiedTransactionBusiness::class,
        UnifiedIncomingTransactionsBusiness::class,
        UnifiedOutGoingTransactionsBusiness::class
    ],

    version = 3,
    exportSchema = true

)
abstract class FinancialAssistantDb : RoomDatabase() {

    abstract fun depositMoneyDao(): DepositMoneyDao
    abstract fun receiveMoneyDao(): ReceiveMoneyDao
    abstract fun sendPochiDao(): SendPochiDao
    abstract fun withdrawMoneyDao(): WithdrawMoneyDao
    abstract fun buyGoodsDao(): BuyGoodsDao
    abstract fun payBillDao(): PayBillDao
    abstract fun sendMoneyDao(): SendMoneyDao
    abstract fun sendGlobalDao(): SendGlobalDao
    abstract fun buyAirtimeDao(): BuyAirtimeDao
    abstract fun sendMshwariDao(): SendMshwariDao
    abstract fun receiveMshwariDao(): ReceiveMshwariDao
    abstract fun bundlesPurchaseDao(): BundlesPurchaseDao
    abstract fun financialAssistantDao(): FinancialAssistantDao
    abstract fun receivePochiDao(): ReceivePochiDao
    abstract fun unifiedTransactionsDao(): UnifiedTransactionsPersonalDao
    abstract fun moveToPochiDao(): MoveToPochiDao
    abstract fun unifiedBusinessDao(): UnifiedTransactionsBusinessDao
    abstract fun moveFromPochiDao(): MoveFromPochiDao
    abstract fun sendFromPochiDao(): SendFromPochiDao
    abstract fun fulizaPayDao(): FulizaPayDao
    abstract fun unknownDao(): UnknownEntityDao

    companion object {
        private var INSTANCE: FinancialAssistantDb? = null

        fun getInstance(context: Context): FinancialAssistantDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FinancialAssistantDb::class.java,
                        "financial_assistant_db"
                    )
                        .addMigrations(MIGRATION_2_3)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}