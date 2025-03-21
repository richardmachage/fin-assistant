package com.transsion.financialassistant.main_activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.onboarding.screens.promt_screens.enable_notifications.EnableNotificationScreen
import com.transsion.financialassistant.onboarding.screens.promt_screens.set_password.SetPasswordScreen
import com.transsion.financialassistant.onboarding.screens.surveys.SurveyScreen
import com.transsion.financialassistant.permissions.readPhoneStatePermission
import com.transsion.financialassistant.permissions.requestPhoneNumberPermissions
import com.transsion.financialassistant.permissions.requestSmsPermissions
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {permissions ->
            val readSmsGranted = permissions[android.Manifest.permission.READ_SMS] ?: false
            val sendSmsGranted = permissions[android.Manifest.permission.SEND_SMS] ?: false
            val readPhoneStateGranted = permissions[android.Manifest.permission.READ_PHONE_STATE] ?: false
            val readPhoneNumberGranted = permissions[android.Manifest.permission.READ_PHONE_NUMBERS] ?: false

            if (readSmsGranted || sendSmsGranted || readPhoneStateGranted || readPhoneNumberGranted) {
                Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Some Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        readPhoneStatePermission(this, requestPermissionLauncher)
        requestSmsPermissions(this, requestPermissionLauncher)
        requestPhoneNumberPermissions(this, requestPermissionLauncher)



       //installSplashScreen()
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            FinancialAssistantTheme {
                val financialAssistantController = rememberNavController()
                 FinancialAssistantNavHost(
                     navController = financialAssistantController,
                     startDestination = OnboardingRoutes.Welcome
                 )

                //SurveyScreen(navController = financialAssistantController)
            }
        }
    }
}


@Composable
fun TestMessageScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val timeTaken by viewModel.timeTaken.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
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

                Text(text = "Time Taken : ${timeTaken.inWholeMilliseconds}")
                VerticalSpacer(10)

                if (loadingState) {
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