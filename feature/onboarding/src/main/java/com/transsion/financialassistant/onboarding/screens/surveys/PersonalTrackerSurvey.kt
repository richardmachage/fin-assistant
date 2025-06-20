package com.transsion.financialassistant.onboarding.screens.surveys

/*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PersonalTrackerSurvey(
    navController: NavController,
    viewModel: SurveyViewModel = hiltViewModel(),
    goToLanding: (route: Any) -> Unit
){
    val categories = listOf(
        stringResource(R.string.baby_clothes) to drawable.baby_02,
        stringResource(R.string.gifts_donations) to drawable.gifts___donations,
        stringResource(R.string.bills_utilities) to drawable.bulb,
        stringResource(R.string.clothing) to drawable.clothing,
        stringResource(R.string.groceries) to drawable.groceries,
        stringResource(R.string.rent) to drawable.rent,
        stringResource(R.string.savings) to drawable.savings,
        stringResource(R.string.transaction_costs) to drawable.transaction,
        stringResource(R.string.entertainment) to drawable.entertainment,
        stringResource(R.string.healthcare) to drawable.healtcare
    )
    val selected by remember { mutableStateOf(false) }

    val selectedItems = remember { mutableStateListOf<String>() }

    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()
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
                        .fillMaxHeight()
                        .padding(start = paddingMedium),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.padding(paddingSmall)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        HorizontalSpacer(8)
                        BigTittleText(
                            text = stringResource(R.string.money_expenditure_personal),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = paddingSmall)
                        )
                    }

                    VerticalSpacer(16)
                    FaintText(
                        text = stringResource(R.string.money_out_categories),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = paddingSmall)
                    )
                    VerticalSpacer(16)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        categories.forEach { (category, iconRes) ->
                            val isSelected = selectedItems.contains(category)
                            FilterChip(

                                selected = isSelected,
                                onClick = {
                                    if (isSelected) selectedItems.remove(category)
                                    else selectedItems.add(category)
                                },
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = iconRes),
                                            contentDescription = "category"
                                        )
                                        HorizontalSpacer(4)
                                        NormalText(text = category)
                                    }

                                },
                                modifier = Modifier
                                    .padding(paddingSmall),
                                shape = RoundedCornerShape(40.dp)
                            )
                        }
                    }
                }
                    FilledButtonFa(
                        text = stringResource(R.string.next_btn),
                        onClick = {
                            //goToLanding()
                            goToLanding(OnboardingRoutes.PersonalTrackerSurvey)

                            viewModel.completeOnboarding()
                        },
                        enabled = if (selectedItems.isNotEmpty()) true else false,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.BottomCenter)
                            .padding(WindowInsets.navigationBars.asPaddingValues())
                    )
                }
            }
        }
    }

@Preview
@Composable
fun PersonalTrackerSurveyPreview(){
    FinancialAssistantTheme {
      // PersonalTrackerSurvey()
    }
}*/
