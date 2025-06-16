package com.transsion.financialassistant.settings.screens.settings

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.transsion.financialassistant.background.workers.SyncTransactionsWorker
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.repository.pin.PinRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: DatastorePreferences,
    private val pinRepo: PinRepo
) : ViewModel() {

    private var _state = MutableStateFlow(SettingScreenState())
    val state = _state.asStateFlow()


    private var _isPinSet = MutableStateFlow(pinRepo.isPinSet())
    val isPinSet = _isPinSet.asStateFlow()

    var showDialog = mutableStateOf(false)


    fun refreshIsPinSet() {
        _isPinSet.value = pinRepo.isPinSet()
    }


    fun onDisableAuth() {
        pinRepo.deletePin()
        refreshIsPinSet()
    }


//    private var workInfo: StateFlow<WorkInfo?>? = null

    private var _refreshProgress = MutableStateFlow(0f)
    val refreshProgress = _refreshProgress.asStateFlow()

    private var _syncAllProgress = MutableStateFlow(0f)
    val syncAllProgress = _syncAllProgress.asStateFlow()


    fun onRefreshTransactions(
        onComplete: () -> Unit
    ) {
        //initialize worker
        val workerParam = Data.Builder()
            .putBoolean(key = "is_full_sync", value = false)
            .build()

        val workManager = WorkManager.getInstance(context)

        val syncTransactionsWorkRequest =
            OneTimeWorkRequestBuilder<SyncTransactionsWorker>()
                .setInputData(workerParam)
                .build()

        workManager.let { wm ->

            wm.enqueue(syncTransactionsWorkRequest)

            //observe
            val workInfo = wm.getWorkInfoByIdFlow(syncTransactionsWorkRequest.id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = null
                )

            viewModelScope.launch {
                workInfo.collect { info ->
                    //real time progress update
                    _refreshProgress.update {
                        info?.progress?.getFloat("progress", 0f) ?: 0f
                    }

                    when (info?.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            onComplete()
                            _state.update { it.copy(showRefreshLoading = false) }

                        }

                        WorkInfo.State.FAILED -> {
                            onComplete()
                            _state.update { it.copy(showRefreshLoading = false) }
                        }

                        WorkInfo.State.ENQUEUED -> {
                            _state.update { it.copy(showRefreshLoading = true) }
                        }

                        WorkInfo.State.RUNNING -> {

                        }

                        WorkInfo.State.BLOCKED -> {
                            _state.update { it.copy(showRefreshLoading = false) }

                        }

                        WorkInfo.State.CANCELLED -> {
                            _state.update { it.copy(showRefreshLoading = false) }
                        }

                        null -> {

                        }
                    }
                }
            }


        }

    }

    fun onSyncAllTransactions(onComplete: () -> Unit) {


        val workerParam = Data.Builder()
            .putBoolean(key = "is_full_sync", value = true)
            .build()


        val workManager = WorkManager.getInstance(context)

        val syncTransactionsWorkRequest =
            OneTimeWorkRequestBuilder<SyncTransactionsWorker>()
                .setInputData(workerParam)
                .build()


        workManager.let { wm ->

            wm.enqueue(syncTransactionsWorkRequest)

            //observe
            val workInfo = wm.getWorkInfoByIdFlow(syncTransactionsWorkRequest.id)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = null
                )

            viewModelScope.launch {
                workInfo.collect { info ->
                    //real time progress update
                    _syncAllProgress.update {
                        info?.progress?.getFloat("progress", 0f) ?: 0f
                    }

                    when (info?.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            onComplete()
                            _state.update { it.copy(showSyncAllLoading = false) }

                        }

                        WorkInfo.State.FAILED -> {
                            onComplete()
                            _state.update { it.copy(showSyncAllLoading = false) }
                        }

                        WorkInfo.State.ENQUEUED -> {
                            _state.update { it.copy(showSyncAllLoading = true) }
                        }

                        WorkInfo.State.RUNNING -> {

                        }

                        WorkInfo.State.BLOCKED -> {
                            _state.update { it.copy(showSyncAllLoading = false) }

                        }

                        WorkInfo.State.CANCELLED -> {
                            _state.update { it.copy(showSyncAllLoading = false) }
                        }

                        null -> {

                        }
                    }
                }
            }


        }

        /* viewModelScope.launch {
             _state.update { it.copy(showSyncAllLoading = true) }
             (0..100).map { progress ->
                 val prog = progress.toFloat() / 100
                 delay(50)
                 _syncAllProgress.update {prog}
                 Log.d("SyncAll", "Progress:$progress")
             }
             _state.update { it.copy(showSyncAllLoading = false) }
             onComplete()
         }*/
    }
}