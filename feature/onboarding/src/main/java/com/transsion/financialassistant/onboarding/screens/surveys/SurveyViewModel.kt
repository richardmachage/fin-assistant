package com.transsion.financialassistant.onboarding.screens.surveys

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.screens.surveys.utils.AnswerType
import com.transsion.financialassistant.onboarding.screens.surveys.utils.SurveyQuestion
import com.transsion.financialassistant.onboarding.screens.surveys.utils.SurveyState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
   @ApplicationContext private val context: Context
) : ViewModel() {
    private val _surveyState = MutableLiveData(SurveyState())
    val surveyState: LiveData<SurveyState> = _surveyState

    private val _currentStep = mutableStateOf(1) // Start at step 1
    val currentStep: State<Int> = _currentStep

    private val questions = listOf(

        SurveyQuestion(1,
            context.getString(R.string.what_type_of_business_do_you_do),
            questionDescription = null,
            AnswerType.SINGLE_CHOICE, listOf(
                context.getString(R.string.retail),
                context.getString(R.string.food_beverage),
                context.getString(R.string.agriculture),
                context.getString(R.string.freelancer_consultant),
                context.getString(R.string.manufacturing),
                context.getString(R.string.online_selling),
                context.getString(R.string.services),
                context.getString(R.string.other),
                )),

        SurveyQuestion(2,
            context.getString(R.string.what_expenses_does_your_business_incur),
            questionDescription = null,
            AnswerType.MULTI_CHOICE, listOf(
                context.getString(R.string.rent_utilities),
                context.getString(R.string.marketing),
                context.getString(R.string.salaries_wages),
                context.getString(R.string.professional_services),
                context.getString(R.string.travel_transportation),
                context.getString(R.string.savings),
                context.getString(R.string.transaction_costs),
                context.getString(R.string.other),
                )),
        SurveyQuestion(3,
            context.getString(R.string.how_does_your_business_receive_income),
            questionDescription = null,
            AnswerType.MULTI_CHOICE_PAY, listOf(
                context.getString(R.string.cash),
                context.getString(R.string.pochi_la_biashara),
//                context.getString(R.string.from_clients_customers),
//                context.getString(R.string.other),
                ))
    )

    init {
        _surveyState.value = SurveyState(
            currentQuestionIndex = 0, // Start from first question
            currentQuestion = questions[0], // Ensure first question loads
            totalQuestions = questions.size
        )
    }

    // Move to the next question
     fun loadNextQuestion(){
        _surveyState.value?.let { currentState ->
            val nextIndex = currentState.currentQuestionIndex + 1

            if (nextIndex < questions.size) {
                _surveyState.value = currentState.copy(
                    currentQuestionIndex = nextIndex,
                    currentQuestion = questions[nextIndex]
                )
                _currentStep.value = nextIndex + 1 // Update progress
            } else {
                _surveyState.value = currentState.copy(isSurveyComplete = true)
            }
        }
    }

    fun loadPreviousQuestion() {
        _surveyState.value?.let { currentState ->
            val prevIndex = currentState.currentQuestionIndex - 1

            if (prevIndex >= 0) {
                _surveyState.value = currentState.copy(
                    currentQuestionIndex = prevIndex,
                    currentQuestion = questions[prevIndex]
                )
                _currentStep.value = prevIndex + 1 // Update progress
            }
        }
    }

    fun answerQuestion(questionId: Int, answer: Any) {
        _surveyState.value?.let { currentState ->
            val updatedAnswers = currentState.answers.toMutableMap()
            updatedAnswers[questionId] = answer
            _surveyState.value = currentState.copy(answers = updatedAnswers)
        }
    }

    // Example for handling multi-choice answers
    fun addOrRemoveMultiChoiceAnswer(questionId: Int, option: String) {
        _surveyState.value?.let { currentState ->
            val currentAnswer = currentState.answers[questionId] as? MutableList<String> ?: mutableListOf()
            if (currentAnswer.contains(option)) {
                currentAnswer.remove(option)
            } else {
                currentAnswer.add(option)
            }
            answerQuestion(questionId, currentAnswer)
        }
    }

    // Simulate loading state (for demonstration with Animated Content View)
    fun submitAnswers() {
        viewModelScope.launch {
            _surveyState.value = _surveyState.value?.copy(isLoading = true)
            delay(2000) // Simulate network request
            // Process the answers here
            _surveyState.value = _surveyState.value?.copy(isLoading = false, isSurveyComplete = true)
            // Optionally reset the survey state
        }
    }

    // calculate progress for the progress bar
    val progress: LiveData<Float> = _surveyState.map { state ->
        if (questions.isNotEmpty()) {
            (state.currentQuestionIndex.toFloat() / questions.size.toFloat())
        } else {
            0f
        }
    }
}