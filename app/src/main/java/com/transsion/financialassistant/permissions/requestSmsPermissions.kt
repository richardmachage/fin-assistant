package com.transsion.financialassistant.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


fun requestSmsPermissions(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>
) {
    val permissionsToRequest = mutableListOf<String>()

    // SMS Permissions (Send and READ)
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.READ_SMS)
    }

    // SEND SMS Permissions
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.SEND_SMS)
    }

    // READ PHONE NUMBERS Permissions
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.READ_PHONE_NUMBERS)
    }

    // READ PHONE STATE Permissions
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.READ_PHONE_STATE)
    }

    if (permissionsToRequest.isNotEmpty()) {
        requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        // Permissions already granted.
        Log.d("READ SMS", "Permissions already granted")
        /*Toast.makeText(context, "Read and Send SMS permissions granted", Toast.LENGTH_SHORT)
            .show()*/

    }
}
