package com.transsion.financialassistant.feedback.di

import com.transsion.financialassistant.feedback.data.FeedBackRepoImpl
import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)

abstract class FeedbackModule {

    @Binds
    abstract fun bindsFeedbackRepo(feedBackRepoImpl: FeedBackRepoImpl): FeedBackRepo

}