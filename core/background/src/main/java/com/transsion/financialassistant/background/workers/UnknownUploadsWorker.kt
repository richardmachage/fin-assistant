package com.transsion.financialassistant.background.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.transsion.financialassistant.background.model.MetricsDto
import com.transsion.financialassistant.background.model.UnknownDto
import com.transsion.financialassistant.background.model.getDeviceMetadata
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.Metrics
import com.transsion.financialassistant.data.repository.transaction.unknown.UnknownRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

@HiltWorker
class UnknownUploadsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val unknownRepo: UnknownRepo,
    private val firestore: FirebaseFirestore,
    private val dataStore: DatastorePreferences
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {


        //first check if there is pending data
        val pendingData = unknownRepo.getAll()
        if (pendingData.isEmpty()) return Result.success() //This line ensures work does nothing if there's nothing to sync

        val deviceMetadata = getDeviceMetadata(context)

        val batches = pendingData.chunked(50)

        for (batchList in batches) {

            val batch = firestore.batch()

            for (item in batchList) {
                val docRef = firestore.collection("UnknownMessages").document()
                batch.set(docRef, UnknownDto(unknownEntity = item, deviceMetadata = deviceMetadata))
            }
            try {
                batch.commit().await()
                //upload successful now delete the batch from the database
                unknownRepo.delete(batchList)
            } catch (e: Exception) {
                Log.e("UnknownUploadsWorker", "Error uploading batch: ${e.message}")
            }
        }


        //now upload the metrics
        val installationId = FirebaseInstallations.getInstance().id.await()
        val scope = CoroutineScope(Dispatchers.IO)
        installationId?.let {
            //get the metrics from datastore
            val jsonMetric =
                dataStore.getValue(key = DatastorePreferences.MESSAGE_PARSING_METRICS, "").first()

            val metric = Json.decodeFromString<Metrics>(jsonMetric)

            firestore.collection("Parsing Metrics").document(it)
                .set(MetricsDto(deviceMetadata = deviceMetadata, metrics = metric))
                .addOnSuccessListener {
                    //delete the metric
                    scope.launch {
                        dataStore.deleteValue(key = DatastorePreferences.MESSAGE_PARSING_METRICS)
                    }
                    Log.d("UnknownUploadsWorker", "Metrics uploaded successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("UnknownUploadsWorker", "Error uploading metrics: ${e.message}")
                }
        }

        return Result.success()
    }

}