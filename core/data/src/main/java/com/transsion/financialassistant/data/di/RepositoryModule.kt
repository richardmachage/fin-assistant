package com.transsion.financialassistant.data.di

import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindTransactionRepo(
        transactionRepoImpl: TransactionRepoImpl
    ): TransactionRepo
}