package com.transsion.financialassistant.home.screens.all_transactions.di

import com.transsion.financialassistant.home.screens.all_transactions.data.AllTransactionsRepoImpl
import com.transsion.financialassistant.home.screens.all_transactions.domain.AllTransactionsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AllTransactionsModule {

    @Binds
    abstract fun bindAllTransactionsRepo(
        allTransactionsRepoImpl: AllTransactionsRepoImpl
    ): AllTransactionsRepo

}