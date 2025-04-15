package com.transsion.financialassistant.presentation.landing_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.transsion.financialassistant.background.workers.InsertTransactionsWorker
import com.transsion.financialassistant.data.preferences.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    var workerId: UUID? = null
    var workManager: WorkManager? = null
    var workInfo: StateFlow<WorkInfo?>? = null

    private var _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var _currentType = MutableStateFlow("Starting...")
    val currentType = _currentType.asStateFlow()

    private var _workerState = MutableStateFlow("Process not started")
    val workerState = _workerState.asStateFlow()


    fun readMessagesAndSave(onFinish: (String) -> Unit) {
        // Step 1: Initialize WorkManager instance
        workManager = WorkManager.getInstance(context)

        // Step 2: Create a one-time work request for the InsertTransactionsWorker
        val insertTransactionsRequest =
            OneTimeWorkRequestBuilder<InsertTransactionsWorker>().build()

        // Store the worker ID to track its status later
        workerId = insertTransactionsRequest.id

        workManager?.let { wm ->
            // Step 3: Enqueue the worker to be executed
            wm.enqueue(insertTransactionsRequest)

            // Step 4: Observe the state of the work using WorkManager's Flow API
            workInfo = wm.getWorkInfoByIdFlow(insertTransactionsRequest.id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = null
                )

            // Step 5: Launch a coroutine to react to work state changes
            viewModelScope.launch {
                workInfo?.collect { info ->
                    //real time progress updates
                    _progress.update {
                        info?.progress?.getFloat("progress", 0f) ?: 0f
                    }
                    _currentType.update {
                        info?.progress?.getString("currentType") ?: "Starting..."
                    }


                    // Step 6: If work succeeds, proceed to mark messages as read
                    when (info?.state) {
                        WorkInfo.State.ENQUEUED -> {
                            _workerState.value = "Initializing Process"
                        }

                        WorkInfo.State.RUNNING -> {
                            _workerState.value = "Process is running"
                        }

                        WorkInfo.State.SUCCEEDED -> {
                            _workerState.value = "Completed successfully"

                            markMessagesRead {
                                onFinish("Success")
                            }
                        }

                        WorkInfo.State.FAILED -> {
                            _workerState.value = "Failed to Complete"
                            onFinish("Failed")
                        }

                        WorkInfo.State.BLOCKED -> {
                            _workerState.value = "Process Blocked"

                        }

                        WorkInfo.State.CANCELLED -> {
                            _workerState.value = "Process Canceled"

                        }

                        null -> {
                            _workerState.value = "Initializing Process"
                        }
                    }
                }
            }
        }
    }


    fun isMessagesRead(): Boolean {
        return sharedPreferences.loadData(SharedPreferences.IS_MESSAGES_READ).toBoolean()
    }

    private fun markMessagesRead(
        onSuccess: () -> Unit
    ) {
        sharedPreferences.saveData(SharedPreferences.IS_MESSAGES_READ, "true")
        onSuccess()
    }

    fun markMessagesUnread() {
        sharedPreferences.saveData(SharedPreferences.IS_MESSAGES_READ, "false")
    }

}