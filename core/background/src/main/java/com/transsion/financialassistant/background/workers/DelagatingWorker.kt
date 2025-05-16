package com.transsion.financialassistant.background.workers

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
for use in tests to instanciate the worker
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltWorkerFactoryEntryPoint {
    fun hiltWorkerFactory(): HiltWorkerFactory
}

val constraints =
    androidx.work.Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
val workRequest = OneTimeWorkRequestBuilder<UnknownUploadsWorker>()
    .setConstraints(constraints)
    .build()

