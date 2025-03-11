package com.transsion.financialassistant.onboarding.screens.create_pin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.text_input_fields.TextFieldFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingSmall


@Composable
fun CreatePinScreen(
    navController: NavController = rememberNavController()
) {
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
                   value = "",
                   onValueChange = {},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.pin)
               )

                VerticalSpacer(16)
                NormalText(
                    text = stringResource(R.string.confirm_pin),
                    textAlign = TextAlign.Start,
                    textColor = Color.Black,
                    modifier = Modifier.padding(start = 60.dp)
                )
                VerticalSpacer(8)
               TextFieldFa(
                   value = "",
                   onValueChange = {},
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   placeholder = stringResource(R.string.confirm_pin)
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