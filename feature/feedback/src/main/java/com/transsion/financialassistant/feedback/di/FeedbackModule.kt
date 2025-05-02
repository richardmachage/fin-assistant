package com.transsion.financialassistant.feedback.di

import com.transsion.financialassistant.feedback.data.FeedBackRepoImpl
import com.transsion.financialassistant.feedback.domain.FeedBackRepo
import dagger.Binds

abstract class FeedbackModule {

    @Binds
    abstract fun bindsFeedbackRepo(feedBackRepoImpl: FeedBackRepoImpl): FeedBackRepo

}