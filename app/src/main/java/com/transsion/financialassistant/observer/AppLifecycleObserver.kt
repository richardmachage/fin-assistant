package com.transsion.financialassistant.observer

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.WindowManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver(private val activity: Activity): DefaultLifecycleObserver {
    private var dialog: AlertDialog? = null

   override fun onStart(owner: LifecycleOwner) {
       /**when app comes to foreground*/
       dismissDialog()
   }

    override fun onStop(owner: LifecycleOwner) {
        /**when app comes to background*/
        showDialog("App is in background")
    }

    private fun showDialog(message: String) {
        if (dialog?.isShowing == true) return

        dialog = AlertDialog.Builder(activity)
            .setMessage(message)
            .setCancelable(false)
            .create()

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.show()
    }

    private fun dismissDialog() {
        dialog?.dismiss()
    }
}