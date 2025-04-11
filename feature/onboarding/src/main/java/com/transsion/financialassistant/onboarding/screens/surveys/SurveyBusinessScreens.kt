package com.transsion.financialassistant.onboarding.screens.surveys

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.onboarding.screens.surveys.utils.AnswerType
import com.transsion.financialassistant.onboarding.screens.surveys.utils.SurveyState
import com.transsion.financialassistant.presentation.R.drawable
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium

@OptIn(ExperimentalLayoutApi::class)
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

    val optionsPay = listOf(
        stringResource(R.string.cash) to drawable.cash,
        stringResource(R.string.pochi_la_biashara) to drawable.mobile_security,
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                            IconButton(
                                onClick = {
                                    surveyViewModel.loadPreviousQuestion()
                                    //navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    contentDescription = "Back",
                                    modifier = Modifier
                                )
                            }
                            HorizontalSpacer(8)
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
                                                            if (isSystemInDarkTheme()) FAColors.cardBackgroundDark else FAColors.cardBackgroundLight
                                                    )
                                                ) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        RadioButton(
                                                            selected = state.answers[it.id] == option,
                                                            onClick = {
                                                                surveyViewModel.answerQuestion(
                                                                    it.id,
                                                                    option
                                                                )
                                                            }
                                                        )
                                                        VerticalSpacer(8)
                                                        NormalText(text = option)
                                                    }
                                                }
                                            }
                                            VerticalSpacer(16)
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
                                                        modifier = Modifier.padding(4.dp)
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


                                    AnswerType.TEXT_INPUT -> {
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
                                            label = { NormalText(text = "Your Answer") },
                                            modifier = Modifier.fillMaxWidth()
                                        )
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
                    onClick = {
                        if (state.currentQuestion == null) return@FilledButtonFa

                        // store answers
                        surveyViewModel.validateAndMoveToNextQuestion() // Proceed to the next question

                        // check if survey is completed
                        if (state.isSurveyComplete) {
                            surveyViewModel.completeOnboarding() // save data when survey is completed
                        }
                    },
                    enabled = state.currentQuestion?.let { question ->
                        val answer = state.answers[question.id]
                        when(question.answerType) {
                            AnswerType.SINGLE_CHOICE -> answer is String && answer.isNotBlank()
                            AnswerType.MULTI_CHOICE,
                            AnswerType.MULTI_CHOICE_PAY -> answer is List<*> && answer.isNotEmpty()
                            AnswerType.TEXT_INPUT -> answer is String && answer.isNotBlank()
                        }
                    }?: false

                    )
            }
        }
    }
}
