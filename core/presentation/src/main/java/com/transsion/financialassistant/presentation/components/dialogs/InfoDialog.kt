package com.transsion.financialassistant.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.transsion.financialassistant.presentation.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDialog(
    showDialog: Boolean = false,
    title: String = "Confirm Dialog",
    message: String = "Are you sure you want to leave?",
    buttonText: String = stringResource(R.string.okay),
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        BasicAlertDialog(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceBright,
                    shape = RoundedCornerShape(10)
                )
                .padding(10.dp)
                .padding(top = 10.dp),
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = true
            )
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Tittle
                TitleText(
                    modifier = Modifier
                        .padding(paddingSmall),
                    text = title
                )
                VerticalSpacer(10)
                //message
                NormalText(
                    text = message
                )

                VerticalSpacer(10)

                //Button
                FilledButtonFa(
                    text = buttonText,
                    onClick = { onDismiss() }
                )

            }
        }
    }
}
