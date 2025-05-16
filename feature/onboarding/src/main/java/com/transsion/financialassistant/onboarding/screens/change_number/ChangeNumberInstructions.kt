package com.transsion.financialassistant.onboarding.screens.change_number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@Composable
fun ChangePhoneNumberInstructions(
    navController: NavController
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
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
                    HorizontalSpacer(2)
                    BigTittleText(text = stringResource(R.string.change_number))
                }
                VerticalSpacer(8)
                FaintText(text = stringResource(R.string.change_number_instructions))
                VerticalSpacer(32)
                Column (modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(paddingLarge),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    NormalText(
                        text = stringResource(R.string.change_number_instructions_1),
                        textAlign = TextAlign.Start
                    )
                    NormalText(
                        text = stringResource(R.string.change_number_instructions_2),
                        textAlign = TextAlign.Start
                    )
                    NormalText(
                        text = stringResource(R.string.change_number_instructions_3),
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
    }
}

@PreviewLightDark
@Composable
fun ChangeNumberInstructionsPreview() {
    FinancialAssistantTheme {
        ChangePhoneNumberInstructions(rememberNavController())
    }
}