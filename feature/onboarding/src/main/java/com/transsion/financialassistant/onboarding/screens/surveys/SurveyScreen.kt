package com.transsion.financialassistant.onboarding.screens.surveys

/*
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
                navController.navigate(OnboardingRoutes.SurveyScreen)
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
                        when (selectedOption) {
                            stringOptions[0] -> navController.navigate(
                                OnboardingRoutes.PersonalTrackerSurvey
                            ){popUpTo(OnboardingRoutes.SurveyScreen){inclusive = true}}

                            stringOptions[1] -> navController.navigate(
                                OnboardingRoutes.SurveyBusinessScreens
                            ){popUpTo(OnboardingRoutes.SurveyScreen){inclusive = true}}

                            stringOptions[2] -> navController.navigate(
                                OnboardingRoutes.SurveyBusinessScreens
                            ){popUpTo(OnboardingRoutes.SurveyScreen){inclusive = true}}
                        }

                        viewModel.setCompleteOnboarding()
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
}*/
