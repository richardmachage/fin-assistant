package com.transsion.financialassistant.onboarding.screens.surveys

/*@OptIn(ExperimentalLayoutApi::class)
@Composable

fun SurveyBusinessScreens(
    navController: NavController,
    surveyViewModel: SurveyViewModel = hiltViewModel(),
    goToLanding: (route: Any) -> Unit
) {
    val state by surveyViewModel.surveyState.observeAsState(SurveyState())

    val optionIcons = mapOf(
        stringResource(R.string.rent_utilities) to drawable.rent,
        stringResource(R.string.marketing) to drawable.promotion,
        stringResource(R.string.salaries_wages) to drawable.bulb,
        stringResource(R.string.professional_services) to drawable.shopping_basket_03,
        stringResource(R.string.travel_transportation) to drawable.travel,
        stringResource(R.string.savings) to drawable.savings,
        stringResource(R.string.transaction_costs) to drawable.transaction,
        stringResource(R.string.stock) to drawable.store_02,
    )

    // Handle back navigation with device back button
    BackHandler(enabled = true) {
        if (state.currentQuestionIndex > 0) {
            surveyViewModel.loadPreviousQuestion()
        } else {
            // If already at the first question, exit or pop the stack
            navController.popBackStack()
        }
    }

    Scaffold {  innerPadding ->
        val paddingValues = WindowInsets.navigationBars.asPaddingValues()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),

            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = paddingMedium),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    VerticalSpacer(48)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = paddingLarge),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SurveyProgressBar(surveyViewModel)
                        }
                    }

                    VerticalSpacer(16)


                    AnimatedContent(
                        targetState = state.currentQuestion,
                        transitionSpec = {
                            if ((targetState?.id ?: 1) > (initialState?.id ?: 1)) {
                                slideInHorizontally(initialOffsetX = { it }) togetherWith
                                        slideOutHorizontally(targetOffsetX = { -it })
                            } else {
                                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                                        slideOutHorizontally(targetOffsetX = { it })
                            }.using(sizeTransform = SizeTransform(clip = false))
                        },
                        label = "questionAnimation"
                    ) { currentQuestion ->
                        currentQuestion?.let {
                            Column {
                                BigTittleText(
                                    text = it.questionText,
                                    textAlign = TextAlign.Start
                                )

                                VerticalSpacer(16)

                                FaintText(
                                    text = it.questionDescription ?: "",
                                    textAlign = TextAlign.Start
                                )

                                VerticalSpacer(16)

                                when (it.answerType) {
                                    AnswerType.SINGLE_CHOICE -> {
                                        var selectedOption by remember {
                                            mutableStateOf(state.answers[it.id]?.toString() ?: "")
                                        }
                                        var other by remember { mutableStateOf("") }

                                        it.options?.forEach { option ->
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
                                                            if (isSystemInDarkTheme()) FAColors.cardBackgroundDark
                                                            else FAColors.cardBackgroundLight
                                                    )
                                                ) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                selectedOption = option
                                                                surveyViewModel.answerQuestion(it.id, option)
                                                            },
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        RadioButton(
                                                            selected = selectedOption == option,
                                                            onClick = {
                                                                selectedOption = option
                                                                surveyViewModel.answerQuestion(it.id, option)
                                                            }
                                                        )
                                                        VerticalSpacer(8)
                                                        NormalText(text = option)
                                                    }
                                                }
                                            }
                                            VerticalSpacer(16)
                                        }
                                        // Other Option
                                        if (selectedOption == "Other") {
                                            var text by remember {
                                                mutableStateOf(
                                                    state.answers[it.id]?.toString() ?: ""
                                                )
                                            }
                                            OutlinedTextField(
                                                value = text,
                                                onValueChange = { newValue ->
                                                    text = newValue
                                                    surveyViewModel.answerQuestion(it.id, newValue)
                                                },
                                                label = { NormalText(text = "Custom Business Type") },
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }


                                    AnswerType.MULTI_CHOICE -> {
                                        it.options?.let { options ->
                                            val selectedItems =
                                                remember { mutableStateListOf<String>() }

                                            FlowRow(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                options.forEach { option ->
                                                    val isSelected = selectedItems.contains(option)
                                                    val iconRes = optionIcons[option]
                                                        ?: R.drawable.stash_question // Fallback icon

                                                    FilterChip(
                                                        selected = isSelected,
                                                        onClick = {
                                                            if (isSelected) selectedItems.remove(
                                                                option
                                                            )
                                                            else selectedItems.add(option)

                                                            // Update ViewModel with selected choices
                                                            surveyViewModel.answerQuestion(
                                                                it.id,
                                                                selectedItems.toList()
                                                            )
                                                        },
                                                        label = {
                                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                                Icon(
                                                                    painter = painterResource(id = iconRes),
                                                                    contentDescription = option
                                                                )
                                                                HorizontalSpacer(4)
                                                                Text(option)
                                                            }
                                                        },
                                                        modifier = Modifier.padding(4.dp),
                                                        shape = RoundedCornerShape(40.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    AnswerType.MULTI_CHOICE_PAY -> {
                                        it.options?.let { optionsPay ->
                                            val selectedItems =
                                                remember { mutableStateListOf<String>() }

                                            LazyVerticalGrid(
                                                columns = GridCells.Fixed(2),
                                                modifier = Modifier.fillMaxSize(),
                                                contentPadding = paddingValues
                                            ) {
                                                val orderedOptions = listOf(
                                                    "Cash" to drawable.cash,
                                                    "Send Money" to drawable.mobile_security,
                                                    "Pochi La Biashara" to drawable.mobile_security
                                                )

                                                items(orderedOptions) { (option, iconRes) ->
                                                    val isSelected = selectedItems.contains(option)

                                                    Card(
                                                        shape = RoundedCornerShape(12.dp),
                                                        modifier = Modifier
                                                            .padding(8.dp)
                                                            .fillMaxWidth()
                                                            .height(120.dp)
                                                            .border(
                                                                width = if (isSelected) 2.dp else 1.dp,
                                                                color = if (isSelected) FAColors.lightGreen else Color.LightGray,
                                                                shape = RoundedCornerShape(12.dp)
                                                            )
                                                            .clickable {
                                                                if (isSelected) selectedItems.remove(
                                                                    option
                                                                )
                                                                else selectedItems.add(option)

                                                                // Update ViewModel
                                                                surveyViewModel.answerQuestion(
                                                                    it.id,
                                                                    selectedItems.toList()
                                                                )
                                                            }
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(12.dp)
                                                        ) {
                                                            // Option Name at Top Start
                                                            Text(
                                                                text = option,
                                                                fontSize = 16.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.Black,
                                                                modifier = Modifier.align(Alignment.TopStart)
                                                            )

                                                            // Selected Indicator at TopEnd
                                                            if (isSelected) {
                                                                Icon(
                                                                    imageVector = Icons.Filled.CheckCircle,
                                                                    contentDescription = "Selected",
                                                                    tint = FAColors.lightGreen,
                                                                    modifier = Modifier.align(
                                                                        Alignment.TopEnd
                                                                    )
                                                                )
                                                            }

                                                            // Amount at Center
                                                            Text(
                                                                text = "KES 6,900.90", // Replace with dynamic value
                                                                fontSize = 14.sp,
                                                                color = Color.Gray,
                                                                modifier = Modifier.align(Alignment.Center)
                                                            )

                                                            // Icon at Bottom Start
                                                            Icon(
                                                                painter = painterResource(id = iconRes),
                                                                contentDescription = option,
                                                                tint = if (isSelected) FAColors.lightGreen else Color.Gray,
                                                                modifier = Modifier.align(Alignment.BottomStart)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    else -> Text("Unsupported answer type")
                                }
                            }
                        }
                    }

                    // Dynamic Spacer to push loading and completion content to the bottom
                    Spacer(modifier = Modifier.weight(1f))

                    if (state.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    if (state.isSurveyComplete) {
                       goToLanding(OnboardingRoutes.SurveyBusinessScreens)
                    }

                    state.error?.let {
                        Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            // Next Button
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = paddingLarge),
                contentAlignment = Alignment.Center
            ) {
                FilledButtonFa(
                    text = stringResource(R.string.next),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (state.currentQuestion == null) return@FilledButtonFa

                        // store answers
                        surveyViewModel.validateAndMoveToNextQuestion() // Proceed to the next question

                        // check if survey is completed
                        if (state.isSurveyComplete) {
                            // save data when survey is completed
                            surveyViewModel.completeOnboarding()
                        }
                    },
                    enabled = state.currentQuestion?.let { question ->
                        val answer = state.answers[question.id]
                        when (question.answerType) {
                            AnswerType.SINGLE_CHOICE -> answer is String && answer.isNotBlank()
                            AnswerType.MULTI_CHOICE,
                            AnswerType.MULTI_CHOICE_PAY -> answer is List<*> && answer.isNotEmpty()

                            AnswerType.TEXT_INPUT -> answer is String && answer.isNotBlank()
                        }
                    } ?: false,

                    )

            }
        }
    }
}*/
