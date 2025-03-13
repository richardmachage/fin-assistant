package com.transsion.financialassistant.onboarding.screens.create_pin

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var isMatching = pin.isNotEmpty() && confirmPin.isNotEmpty() && pin == confirmPin
    val isPinVisible by remember { mutableStateOf(false) }
    val isConfirmPinVisible by remember { mutableStateOf(false) }

    val asteriskVisualTransformation = VisualTransformation { text ->
        val transformedText = AnnotatedString(text.text.map { '*' }
            .joinToString(""))
        TransformedText(transformedText, OffsetMapping.Identity)
    }
    Scaffold(
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
               // .background(Color(0xFFECEEED))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingLarge)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(paddingLarge)
                ) {
                   /* Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(28.dp)
                    ) {*/
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
               TextFieldFa(
                   value = pin,
                   onValueChange = {newPin -> pin = newPin},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.pin),
                   visualTransformation = asteriskVisualTransformation
               )

                VerticalSpacer(16)
                NormalText(
                    text = stringResource(R.string.confirm_pin),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 60.dp)
                )
                VerticalSpacer(8)
               TextFieldFa(
                   value = confirmPin,
                   onValueChange = {newConfirmPin -> confirmPin = newConfirmPin},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.confirm_pin),
                   visualTransformation = asteriskVisualTransformation
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