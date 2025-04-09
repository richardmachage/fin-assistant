package com.transsion.financialassistant.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun requestNotificationPermission(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>
) {
    val permissionsToRequest = mutableListOf<String>()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    if (permissionsToRequest.isNotEmpty()){
        requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        Log.d("PERMISSIONS", "Permissions already granted")
    }
}
