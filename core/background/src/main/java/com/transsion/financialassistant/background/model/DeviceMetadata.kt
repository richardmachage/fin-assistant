package com.transsion.financialassistant.background.model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

data class DeviceMetadata(
    val androidVersion: String? = null,
    val softwareVersion: String? = null,
    val phoneModel: String? = null,
    val appVersion: String? = null
)


fun getDeviceMetadata(context: Context): DeviceMetadata {
    // Get user-visible Android OS version string
    val androidVersion = Build.VERSION.RELEASE


    // Get Software Version
    val softwareVersion = Build.DISPLAY

    // Get the phone model
    val model = Build.MODEL

    // Get application version name
    var applicationVersionName: String
    try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        applicationVersionName = packageInfo.versionName.toString()
    } catch (e: PackageManager.NameNotFoundException) {
        // This should ideally not happen if context.packageName is correct
        // but yeah I gotta handle edge cases because I am.....yeah you guessed it right A good Dev!!!!
        e.printStackTrace()
        applicationVersionName = "Unknown"
    }

    return DeviceMetadata(
        androidVersion = androidVersion,
        softwareVersion = softwareVersion,
        phoneModel = model,
        appVersion = applicationVersionName
    )
}