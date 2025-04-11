package com.transsion.financialassistant.onboarding.screens.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.biometric.BiometricPromptManager
import com.transsion.financialassistant.onboarding.biometric.BiometricResult
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.onboarding.screens.surveys.SurveyViewModel
import com.transsion.financialassistant.presentation.components.CircularLoading
import com.transsion.financialassistant.presentation.components.ThreeDotsLoader
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    surveyViewModel: SurveyViewModel = hiltViewModel(),
    navController: NavController,
    goToLanding: (route: Any) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // BiometricPromptManager for biometric authentication
    val promptManager by remember { mutableStateOf(BiometricPromptManager(context)) }
    val biometricResult by promptManager.promptResult.collectAsState(initial = null)

    // if onboarding is completed, navigate to home screen
    val isOnboardingCompleted by surveyViewModel.onboardingCompleted.observeAsState(initial = false)


    CircularLoading(
        isLoading = state.isLoading,
        loadingMessage = "Logging in"
    )

    //Toast handler
    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let {
            Toast.makeText(context, state.toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.resetToast()
        }
    }



    LaunchedEffect(state.pin) {
        if (state.pin.length == 4) {
            viewModel.validatePin(state.pin)
        }
    }

    LaunchedEffect(state.isValidationSuccess) {
        if (state.isValidationSuccess) {
            viewModel.clearPin()
            if (isOnboardingCompleted) {
                goToLanding(OnboardingRoutes.Login)

            } else {
                navController.navigate(OnboardingRoutes.SurveyScreen) {
                    popUpTo(OnboardingRoutes.Login) { inclusive = true }
                }
            }
        }
    }

    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()

        when (state.isValidationSuccess) {
            //is PinState.Loading -> CircularLoading()
            true -> CircularLoading(true)
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxHeight(0.4f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {

                            BigTittleText(
                                text = stringResource(viewModel.getGreetingBasedOnTime())
                            )
                            VerticalSpacer(8)

                            FaintText(
                                modifier = Modifier.fillMaxWidth(0.7F),
                                text = stringResource(R.string.login_description)
                            )

                        }
                        // PIN Display
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(0.6f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(4) { index ->
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .border(
                                                BorderStroke(2.dp, FAColors.green),
                                                shape = RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (index < state.pin.length) {
                                            NormalText(
                                                text = if (index < state.pin.length) "*" else "",
                                            )
                                        }
                                    }
                                }
                            }

                            VerticalSpacer(8)

                            if (!errorMessage.isNullOrEmpty()) {
                                Text(
                                    text = errorMessage!!,
                                    color = Color.Red,
                                    fontSize = 11.sp
                                )

                                // reset the error message after displaying
                                viewModel.resetErrorMessage()
                            }

                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.1F),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        AnimatedVisibility(
                            visible = state.isLoading
                        ) {
                            ThreeDotsLoader(
                                animationDelay = 500
                            )
                        }
                    }
                    //second half
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.5F)
                            .align(Alignment.BottomCenter)
                    ) {
                        //custom key pad
                        // KeyPad Number Layout
                        val keyPadNumbers = listOf(
                            listOf("1", "2", "3"),
                            listOf("4", "5", "6"),
                            listOf("7", "8", "9")
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            keyPadNumbers.forEach { row ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.8F),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {
                                    row.forEach { digit ->
                                        NumberButton(digit) {
                                            viewModel.onPinChange(digit)
                                            viewModel.onPinEntered(digit)

                                        }
                                    }
                                }
                                VerticalSpacer(8)
                            }

                            // Last row: Fingerprint, 0, Delete
                            Row(
                                modifier = Modifier.fillMaxWidth(0.8F),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                // Fingerprint Button
                                CircularIconButton(com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint) {
                                    // Trigger biometric authentication when fingerprint button is clicked
                                    promptManager.showBiometricPrompt(
                                        title = context.getString(R.string.biometric_authentication),
                                        description = context.getString(R.string.please_authenticate_to_continue)
                                    )

                                }

                                // Zero Button
                                NumberButton("0") {
                                    viewModel.onPinChange("0")
                                    viewModel.onPinEntered(digit = "0")
                                }

                                // Delete Button
                                CircularIconButton(com.transsion.financialassistant.presentation.R.drawable.backspace) {
                                    if (state.pin.isNotEmpty()) viewModel.onBackSpace()
                                }

                                //val intent = Intent(context, LoginActivity::class.java)

                                // Display biometric authentication result
                                biometricResult?.let { result ->
                                    if (result is BiometricResult.AuthenticationSuccess) {
                                        if (isOnboardingCompleted) {
                                            goToLanding(OnboardingRoutes.Login)
                                            /*navController.navigate(*//*OnboardingRoutes.HomeScreen*//*) {
                                                // popUpTo(OnboardingRoutes.Login) { inclusive = true }
                                            }*/
                                        } else {
                                            navController.navigate(OnboardingRoutes.SurveyScreen) {
                                                // popUpTo(OnboardingRoutes.Login) { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


}

// Reusable Number Button
@Composable
fun NumberButton(
    number: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(backgroundColor = FAColors.lightGreen)
    ) {
        Text(
            text = number,
            color = FAColors.green,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Reusable Circular Icon Button
@Composable
fun CircularIconButton(icon: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(FAColors.lightGreen)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = FAColors.green,
            modifier = Modifier.size(32.dp)
        )
    }
}


@PreviewScreenSizes
@Composable
fun PrevLogIn() {
    FinancialAssistantTheme {
        // LoginScreen()
    }
}