package com.transsion.financialassistant.home.di

import com.transsion.financialassistant.home.data.AllTransactionsRepoImpl
import com.transsion.financialassistant.home.domain.RecentTransactionRepo
import com.transsion.financialassistant.home.data.RecentTransactionRepoImpl
import com.transsion.financialassistant.home.domain.AllTransactionsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {

    @Binds
    abstract fun bindAllTransactionsRepo(
        allTransactionsRepoImpl: AllTransactionsRepoImpl
    ): AllTransactionsRepo

    @Binds
    abstract fun bindRecentTransactionRepo(
        recentTransactionRepoImpl: RecentTransactionRepoImpl
    ): RecentTransactionRepo


}