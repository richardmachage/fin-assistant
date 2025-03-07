package com.transsion.financialassistant.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


fun requestSmsPermissions(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>
) {
    val permissionsToRequest = mutableListOf<String>()
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.READ_SMS)
    }
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        permissionsToRequest.add(Manifest.permission.SEND_SMS)
    }
    if (permissionsToRequest.isNotEmpty()) {
        requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        // Permissions already granted.
        Toast.makeText(context, "Read and Send SMS permissions granted", Toast.LENGTH_SHORT)
            .show()

    }
}
