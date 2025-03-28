package com.transsion.financialassistant.background.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class InsertTransactionsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        println("Worker called")
        return Result.success()
    }

}


