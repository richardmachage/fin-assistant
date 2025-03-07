package com.transsion.financialassistant.data.di

import android.content.Context
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.room.db.FinancialAssistantDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatastorePreferences(@ApplicationContext context: Context): DatastorePreferences {
        return DatastorePreferences(context)
    }


    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return SharedPreferences(context)
    }


    @Provides
    @Singleton
    fun providesFinancialAssistantDb(@ApplicationContext context: Context): FinancialAssistantDb {
        return FinancialAssistantDb.getInstance(context)
    }


    //Room DAOs
    @Provides
    @Singleton
    fun providesDepositMoneyDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.depositMoneyDao()

    @Provides
    @Singleton
    fun providesReceiveMoneyDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.receiveMoneyDao()

    @Provides
    @Singleton
    fun providesSendPochiDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.sendPochiDao()

    @Provides
    @Singleton
    fun providesWithdrawMoneyDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.withdrawMoneyDao()

    @Provides
    @Singleton
    fun providesBuyGoodsDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.buyGoodsDao()

    @Provides
    @Singleton
    fun providesPayBillDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.payBillDao()

    @Provides
    @Singleton
    fun providesSendMoneyDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.sendMoneyDao()

    @Provides
    @Singleton
    fun providesSendGlobalDao(financialAssistantDb: FinancialAssistantDb) =
        financialAssistantDb.sendGlobalDao()


}