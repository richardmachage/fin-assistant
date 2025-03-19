package com.transsion.financialassistant.onboarding.screens.confirm_number

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ConfirmNumberDualScreen(
    navController: NavController,
    viewModel: ConfirmNumberViewModel = hiltViewModel(),
    onStart: (String) -> Unit = {}
){
    val phoneNumbers by viewModel.mpesaNumbers.collectAsState()
    var selectedNumber by remember {
        mutableStateOf(
            phoneNumbers.firstOrNull() ?: ""
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getPhoneDualNumbers()
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
                BigTittleText(
                    text = stringResource(R.string.begin_your_smartmoney_journey),
                )
                VerticalSpacer(32)

                FaintText(text = stringResource(R.string.detected_dual_number))

                VerticalSpacer(32)

                if (phoneNumbers.isEmpty()) {
                    Text(stringResource(R.string.no_m_pesa_numbers_detected))
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.select_mpesa_number),
                            style = MaterialTheme.typography.headlineSmall
                        )

                        phoneNumbers.forEach { number ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(number, style = MaterialTheme.typography.bodyMedium)
                                Switch(
                                    checked = number == selectedNumber,
                                    onCheckedChange = { selectedNumber = number }
                                )
                            }
                        }
                    }
                }
            }
                FilledButtonFa(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingLarge)
                        .align(Alignment.BottomCenter)
                        .imePadding(),
                    onClick = { navController.navigate(OnboardingRoutes.SetPassword) }, //{onStart(selectedNumber)},
                    text = stringResource(R.string.get_started),
                )
        }
    }
}