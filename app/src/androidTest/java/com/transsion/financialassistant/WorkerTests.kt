package com.transsion.financialassistant

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.WorkManagerTestInitHelper
import com.transsion.financialassistant.background.workers.HiltWorkerFactoryEntryPoint
import com.transsion.financialassistant.background.workers.InsertTransactionsWorker
import dagger.hilt.android.EntryPointAccessors
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WorkerTests {

    private lateinit var workerFactory: HiltWorkerFactory
    private lateinit var context: Context

    @Before
    fun setUpWorker() {
        context = ApplicationProvider.getApplicationContext()

// Manually retrieve the factory from Hilt's singleton component
        workerFactory = EntryPointAccessors.fromApplication(
            context,
            HiltWorkerFactoryEntryPoint::class.java
        ).hiltWorkerFactory()

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun testInsertTransactionsWorker() {
        val request = OneTimeWorkRequestBuilder<InsertTransactionsWorker>().build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(request).result.get()

        val workInfo = workManager.getWorkInfoById(request.id).get()
        if (workInfo != null) {
            assertEquals(WorkInfo.State.SUCCEEDED, workInfo.state)
        } else {
            fail("WorkInfo is null")
        }
    }

}