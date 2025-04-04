package com.transsion.financialassistant.home.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.rememberSwipeableState
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.dialogs.ConfirmDialog
import com.transsion.financialassistant.presentation.components.dialogs.InfoDialog
import com.transsion.financialassistant.presentation.components.isPermissionGranted
import com.transsion.financialassistant.presentation.components.requestMultiplePermissions
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var isGranted = remember {
        context.isPermissionGranted(Manifest.permission.READ_SMS)
                //&& context.isPermissionGranted(Manifest.permission.READ_PHONE_NUMBERS)
    }

    var showPermissionDialog by remember { mutableStateOf(false) }
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }

    val permission = arrayOf(
        Manifest.permission.READ_SMS,
        //Manifest.permission.READ_PHONE_NUMBERS
    )

    val laucher = requestMultiplePermissions(
        onPermissionGranted = {
            isGranted = true
            showPermissionDialog = false
            showPermissionRationaleDialog = false
        },
        onPermissionDenied = {
            showPermissionRationaleDialog = true
        }
    )

    Scaffold { paddingValues ->

        // Read SMS Permission Dialog
        ConfirmDialog(
            showDialog = showPermissionDialog,
            title = stringResource(R.string.sms_permission_title),
            message = stringResource(R.string.sms_permission_message),
            onDismiss = {
                showPermissionDialog = false
                showPermissionRationaleDialog = true
            },
            onConfirm = {
                showPermissionDialog = false
                laucher.launch(permission)
            },
            confirmButtonText = stringResource(R.string.allow),
            dismissButtonText = stringResource(R.string.deny)
        )

        // Read sms permission rationale dialog
        InfoDialog(
            showDialog = showPermissionRationaleDialog,
            title = stringResource(R.string.sms_permission_title),
            message = stringResource(R.string.sms_permission_rationale),
            onDismiss = {
                showPermissionRationaleDialog = false
                laucher.launch(permission)
            },
            buttonText = stringResource(R.string.allow)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (isGranted) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BigTittleText(
                        text = "Welcome to Financial Assistant"
                    )
                }
            } else {
                showPermissionDialog = true
            }
        }
    }
}