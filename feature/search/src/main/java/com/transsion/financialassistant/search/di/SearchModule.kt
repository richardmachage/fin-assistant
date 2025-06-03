package com.transsion.financialassistant.search.di

import com.transsion.financialassistant.search.data.SearchRepoImpl
import com.transsion.financialassistant.search.domain.SearchRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    @Binds
    abstract fun bindSearchRepo(
        searchRepoImpl: SearchRepoImpl
    ): SearchRepo

}