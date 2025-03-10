package com.transsion.financialassistant.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.transsion.financialassistant.presentation.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ConfirmDialog(
    title: String = "Allow Financial Assistant to access your SIM card",
    message: String = "We need to access your SIM information to set up your Financial Assistant App.",
    confirmButtonText: String = stringResource(R.string.okay),
    dismissButtonText: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //Tittle
            TitleText(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(paddingSmall),
                text = title
            )

            //message
            NormalText(
                modifier = Modifier.align(Alignment.Center),
                text = message
            )

            //Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledButtonFa(
                    text = stringResource(R.string.allow),
                    onClick = { onConfirm() }
                )

                OutlineButtonFa(
                    text = stringResource(R.string.cancel),
                    onClick = { onDismiss() },
                )
            }
        }
    }
}