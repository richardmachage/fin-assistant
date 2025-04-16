package com.transsion.financialassistant.presentation.main_activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantRoutes
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val viewmodel = hiltViewModel<MainViewModel>()
            FinancialAssistantTheme {
                val financialAssistantController = rememberNavController()
                FinancialAssistantNavHost(
                    navController = financialAssistantController,
                    startDestination = OnboardingRoutes.CreatePin
                       // viewmodel.getStartDestination()/*FinancialAssistantRoutes.Landing*/ //InsightsRoutes.Insights //viewmodel.getStartDestination()//OnboardingRoutes.Welcome
                )
            }
        }
    }
}


@Composable
fun TestMessageScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    // val timeTaken by viewModel.timeTaken.collectAsState()
    // val loadingState by viewModel.loadingState.collectAsState()
    val mpesaMessages by viewModel.messages.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Time Taken : ${state.timeTaken.inWholeMilliseconds}")
                VerticalSpacer(10)

                if (state.isLoading) {
                    CircularProgressIndicator(color = FAColors.green)
                }

                VerticalSpacer(10)

                Button(
                    enabled = mpesaMessages.isEmpty(),
                    onClick = { viewModel.getTheMessages(context = context) }) {
                    Text(text = "Get Messages")
                }

                if (mpesaMessages.isNotEmpty()) {
                    Text(text = "Number of messages : ${mpesaMessages.size}")
                }

                if (mpesaMessages.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(mpesaMessages) { index, it ->
                            Log.d("MPESA MESSAGE", it.body)
                            Column(
                                modifier = Modifier.padding(paddingMedium)
                            ) {
                                Text("index: $index")
                                Text(text = "sub Id: " + it.subscriptionId)
                                VerticalSpacer(5)
                                Text(text = "message: " + it.body)
                            }
                        }
                    }
                }

            }
        }
    }
}