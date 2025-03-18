package com.transsion.financialassistant.data.di

import com.transsion.financialassistant.data.repository.security.SecurityRepo
import com.transsion.financialassistant.data.repository.security.SecurityRepoImpl
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindTransactionRepo(
        transactionRepoImpl: TransactionRepoImpl
    ): TransactionRepo

    @Binds
    internal abstract fun bindSecurityRepo(
        securityRepoImpl: SecurityRepoImpl
    ): SecurityRepo
}