package com.transsion.financialassistant.onboarding.screens.surveys

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun SurveyScreen(
    navController: NavController,
    viewModel: SurveyViewModel = hiltViewModel()
) {
    var selected by remember { mutableStateOf(false) }

    val stringOptions = listOf(
        stringResource(R.string.user_need_1),
        stringResource(R.string.user_need_2),
        stringResource(R.string.user_need_3)
    )

    var selectedOption by remember { mutableStateOf(stringOptions[0]) }

    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()

        if (viewModel.onboardingCompleted.value == true){
            LaunchedEffect(Unit) {
                //navController.navigate(OnboardingRoutes.HomeScreen)
            }
        } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .padding(start = paddingMedium),
                    horizontalAlignment = Alignment.Start
                ) {

                    BigTittleText(
                        text = stringResource(R.string.what_do_you_want_to_do),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = paddingSmall)
                    )
                    VerticalSpacer(16)
                    FaintText(
                        text = stringResource(R.string.we_use_this_information),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = paddingSmall)
                    )

                    VerticalSpacer(16)

                    // Row - OnBoarding user needs
                    // Card1 -> RadioButton and Text
                    Column(
                        modifier = Modifier.fillMaxHeight(0.5f)
                    ) {
                        stringOptions.forEach { option ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    elevation = CardDefaults.cardElevation(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor =
                                            if (isSystemInDarkTheme()) FAColors.cardBackgroundDark else FAColors.cardBackgroundLight
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedOption = option
                                            }, // Make the entire row clickable
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedOption == option,
                                            onClick = { selectedOption = option },
                                            enabled = true,
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = FAColors.green,
                                                unselectedColor = Color.Gray,
                                                disabledSelectedColor = Color.Gray,
                                                disabledUnselectedColor = Color.Gray
                                            )
                                        )

                                        NormalText(
                                            text = option,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                            VerticalSpacer(32)
                        }

                    }
                }
                FilledButtonFa(
                    text = stringResource(R.string.next_btn),
                    onClick = {
                        if (selectedOption == stringOptions[0]) navController.navigate(
                            OnboardingRoutes.PersonalTrackerSurvey
                        )
                        else if (selectedOption == stringOptions[1]) navController.navigate(
                            OnboardingRoutes.SurveyBusinessScreens
                        )
                        else if (selectedOption == stringOptions[2]) navController.navigate(
                            OnboardingRoutes.SurveyBusinessScreens
                        )
                    },

                    enabled = if (selectedOption.isNotEmpty()) true else false,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(0.8f),
                )
            }
        }
            }
    }
}

@PreviewLightDark
@Composable
fun SurveyScreenPreview() {
    FinancialAssistantTheme {
        SurveyScreen(navController = rememberNavController())
    }
}