package com.transsion.financialassistant.insights.di

import com.transsion.financialassistant.insights.data.InsightRepoImpl
import com.transsion.financialassistant.insights.domain.InsightsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InsightsModule {

    @Binds
    abstract fun bindInsightsRepo(
        insightsRepoImpl: InsightRepoImpl
    ): InsightsRepo

}