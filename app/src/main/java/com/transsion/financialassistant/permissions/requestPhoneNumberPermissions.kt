package com.transsion.financialassistant.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun requestPhoneNumberPermissions(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>
){
    val permissionsToRequest = mutableListOf<String>()

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS)
    != PackageManager.PERMISSION_GRANTED)
    {
        permissionsToRequest.add(Manifest.permission.READ_PHONE_NUMBERS)
    }

    if (permissionsToRequest.isNotEmpty()) {
        requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
    } else {
        // Permissions already granted.
        Log.d("READ CONTACTS", "Permissions already granted")
    }

}