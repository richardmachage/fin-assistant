package com.transsion.financialassistant.onboarding.screens.create_pin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.text_input_fields.PasswordTextFieldFa
import com.transsion.financialassistant.presentation.components.text_input_fields.TextFieldFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge


@Composable
fun CreatePinScreen(
    navController: NavController = rememberNavController()
) {
    var pin by remember { mutableStateOf("")}
    var confirmPin by remember { mutableStateOf("")}
    var showError by remember { mutableStateOf(false) }
    val context: Context = LocalContext.current
    var isMatching = pin.isNotEmpty() && confirmPin.isNotEmpty() && pin == confirmPin
    val isValidLength = pin.length == 4 && confirmPin.length == 4

    val asteriskVisualTransformation = VisualTransformation { text ->
        val transformedText = AnnotatedString(text.text.map { '*' }
            .joinToString(""))
        TransformedText(transformedText, OffsetMapping.Identity)
    }

    // Show Error Message
//    LaunchedEffect(showError) {
//        if (showError) {
//            val message = when {
//                pin.length != 4 || confirmPin.length != 4 -> "PIN must be 4 characters only!"
//                pin != confirmPin -> "Pins do not match!"
//                else -> null
//            }
//            message?.let {
//                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//            }
//            showError = false
//        }
//    }
    Scaffold(
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(paddingLarge)
                ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            colors = IconButtonDefaults.iconButtonColors().copy(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                  //  }
                    HorizontalSpacer(16)
                    BigTittleText(text = stringResource(R.string.create_pin),)
                }

                VerticalSpacer(4)

                FaintText(
                    modifier = Modifier.padding(start = 60.dp),
                    text = stringResource(R.string.create_pin_description),
                    textAlign = TextAlign.Start,
                )

                VerticalSpacer(16)
                NormalText(
                    text = stringResource(R.string.pin),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 60.dp)
                )
                VerticalSpacer(8)
               PasswordTextFieldFa(
                   value = pin,
                   onValueChange = {newPin -> if (newPin.length < 5) pin = newPin},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.pin),
                   isShowError = showError,
                   visualTransformation = asteriskVisualTransformation
               )

                VerticalSpacer(16)
                NormalText(
                    text = stringResource(R.string.confirm_pin),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 60.dp)
                )
                VerticalSpacer(8)
               PasswordTextFieldFa(
                   value = confirmPin,
                   onValueChange = {newConfirmPin -> if (newConfirmPin.length < 5) confirmPin = newConfirmPin},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.confirm_pin),
                   isShowError = showError,
                   visualTransformation = asteriskVisualTransformation
               )

                if (showError){
                    Text(
                        text = stringResource(R.string.pin_error_message),
                        color = Color.Red,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                VerticalSpacer(80)
                FilledButtonFa(
                    text = stringResource(R.string.create),
                    onClick = {
                        if (isValidLength && isMatching) {
                            navController.navigate("welcome")
                        } else {
                            showError = true
                        }
                    },

                )
            }
        }
        }
    }




@PreviewLightDark()
@Composable
fun CreatePinScreenPreview(){
    FinancialAssistantTheme {
        CreatePinScreen(navController = rememberNavController())
    }
}