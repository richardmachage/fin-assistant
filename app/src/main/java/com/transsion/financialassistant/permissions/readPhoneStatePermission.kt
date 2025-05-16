package com.transsion.financialassistant.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun readPhoneStatePermission(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>
){
    val permissionsToRequest = mutableListOf<String>()

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.READ_PHONE_STATE)
    }

    if (permissionsToRequest.isNotEmpty()) {
        requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        // Permissions already granted.
        Log.d("READ PHONE STATE", "Permissions already granted")
        /*Toast.makeText(context, "Read and Send SMS permissions granted", Toast.LENGTH_SHORT)
            .show()*/

    }

}