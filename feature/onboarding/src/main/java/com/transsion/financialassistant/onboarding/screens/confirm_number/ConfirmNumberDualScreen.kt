package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ConfirmNumberDualScreen(
    navController: NavController,
    viewModel: ConfirmNumberViewModel = hiltViewModel(),
    onStart: (String) -> Unit = {},
    context: Context = LocalContext.current
) {
    /*val phoneNumbers by viewModel.mpesaNumbers.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val selectedNumber by viewModel.selectedNumber.collectAsState()*/

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMpesaNumbers(context)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            //contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val faintTitleText = when (state.mpesaNumbers.size) {
                    2 -> stringResource(R.string.detected_dual_number)
                    1 -> stringResource(R.string.detected_one_number) + " " + state.mpesaNumbers.first()
                    else -> null
                }

                BigTittleText(
                    text = stringResource(R.string.begin_your_smartmoney_journey),
                )
                VerticalSpacer(32)

                faintTitleText?.let {
                    FaintText(text = it)
                }
                VerticalSpacer(32)

                when {
                    state.errorMessage != null -> {
                        NormalText(
                            text = state.errorMessage!!,
                        )
                        NormalText(
                            text = stringResource(R.string.no_m_pesa_numbers_detected),
                            textAlign = TextAlign.Start,
                            textColor = Color.Red,
                            modifier = Modifier.padding(paddingMedium)
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingLarge),
                            shape = RoundedCornerShape(paddingLarge),
                        ) {
                            NormalText(
                                text = stringResource(R.string.change_number_instructions_1),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(paddingMedium)
                            )
                            NormalText(
                                text = stringResource(R.string.change_number_instructions_2),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(paddingMedium)
                            )
                            NormalText(
                                text = stringResource(R.string.change_number_instructions_3),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(paddingMedium)
                            )
                        }
                    }

                    state.mpesaNumbers.isEmpty() -> {
                        NormalText(
                            text = stringResource(R.string.no_m_pesa_numbers_detected),
                            textAlign = TextAlign.Start,
                            textColor = Color.Red
                        )
                    }

                    else -> {
                        when (state.mpesaNumbers.size) {
                            1 -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    NormalText(
                                        text = stringResource(R.string.not_your_number)
                                    )
                                    HorizontalSpacer(5)

                                    ClickableText(
                                        text = stringResource(R.string.use_another_number),
                                        onClick = {
                                            navController.navigate(OnboardingRoutes.ChangeNumber)
                                        },
                                        underline = true
                                    )
                                }
                            }

                            2 -> {
                                Column {
                                    Text(
                                        text = stringResource(R.string.select_mpesa_number),
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    state.mpesaNumbers.forEach { number ->
                                        PhoneNumberItem(
                                            phoneNumber = number,
                                            simSlot = state.mpesaNumbers.indexOf(number) + 1,
                                            isSelected = number == state.selectedNumber,
                                            onToggle = { viewModel.onSelectNumber(number) }
                                        )
                                    }
                                }
                            }
                        }
                        //}
                    }
                }
            }

            FilledButtonFa(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
                    .align(Alignment.BottomCenter),
                onClick = {
                    navController.navigate(OnboardingRoutes.CreatePin) {
                        popUpTo<OnboardingRoutes.ConfirmNumberDual> {
                            inclusive = true
                        }
                    }
                },
                enabled = if (state.mpesaNumbers.isNotEmpty() && state.mpesaNumbers.size == 1) true else state.selectedNumber != null,
                text = stringResource(R.string.get_started),
            )
        }
    }
}


// Composable for two numbers detected
@Composable
fun PhoneNumberItem(
    phoneNumber: String,
    simSlot: Int,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val cardBackgroundColor =
        if (isSystemInDarkTheme()) FAColors.cardBackgroundDark else FAColors.GrayBackground
    val textColor = if (isSystemInDarkTheme()) Color.White else FAColors.black
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = paddingSmall),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor,
            contentColor = FAColors.black
        ),
        shape = RoundedCornerShape(paddingSmall)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingSmall, vertical = paddingLarge),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(paddingSmall)
            ) {
                NormalText(
                    text = phoneNumber,
                    textColor = textColor,
                    modifier = Modifier
                )
                HorizontalSpacer(8)

                Box(
                    modifier = Modifier
                        .background(
                            color = if (simSlot == 1) FAColors.lightGreen else FAColors.lightGreen,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .padding(horizontal = paddingSmall, vertical = paddingSmall)
                ) {
                    NormalText(
                        text = "SIM $simSlot",
                        textColor = FAColors.green,

                        )
                }
            }
            HorizontalSpacer(8)

            Switch(
                modifier = Modifier.padding(end = paddingMedium),
                checked = isSelected,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = FAColors.green,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}