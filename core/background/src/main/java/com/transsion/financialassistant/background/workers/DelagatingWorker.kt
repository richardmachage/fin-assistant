package com.transsion.financialassistant.background.workers

import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun hiltWorkerFactory(): HiltWorkerFactory
}
