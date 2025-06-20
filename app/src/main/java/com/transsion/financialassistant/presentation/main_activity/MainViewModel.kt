package com.transsion.financialassistant.presentation.main_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transsion.financialassistant.data.models.MpesaMessage
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getMpesaMessagesByTransactionType
import com.transsion.financialassistant.data.repository.pin.PinRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionRepo: TransactionRepo,
    private val onboardingRepo: OnboardingRepo,
    private val pinRepo: PinRepo,
) : ViewModel() {


    private var _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()


    private var _messages = MutableStateFlow(emptyList<MpesaMessage>())
    val messages = _messages.asStateFlow()

    private val _requireAuth = MutableStateFlow(false)
    val requireAuth = _requireAuth.asStateFlow()

    fun getTheMessages(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            _messages.update {
                getMpesaMessagesByTransactionType(
                    context = context,
                    filterValue = TransactionType.SEND_MONEY,
                    getExecutionTime = { dur ->
                        _state.update { it.copy(timeTaken = dur) }
                        _state.update { it.copy(isLoading = false) }
                    },
                    transactionRepo = transactionRepo
                )
            }
        }
    }

    fun getStartDestination(): Any {
        //when onboarding has completed
        if (onboardingRepo.hasCompletedOnboarding()) {
            //onboarding has completed, check if pin is set

            return if (pinRepo.isPinSet()) {
                //pin is set, go to login
                OnboardingRoutes.Login
            } else {
                //pin not set, go to home
                FinancialAssistantRoutes.Landing
            }
        } else {
            //Onboarding has not completed
            //show welcome screen
            return OnboardingRoutes.Welcome

        }
    }

    /**Check onResume*/
    fun checkAuthOnResume(){
        if (onboardingRepo.hasCompletedOnboarding() && pinRepo.isPinSet()) {
            _requireAuth.value = true
        }
    }

    fun isPinSet() = onboardingRepo.hasCompletedOnboarding() && pinRepo.isPinSet()

    /**Check if Authentication is Completed*/
    fun authCompleted(){
        _requireAuth.value = false
    }
}