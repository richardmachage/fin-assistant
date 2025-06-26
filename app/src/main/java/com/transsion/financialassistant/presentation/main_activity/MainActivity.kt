package com.transsion.financialassistant.presentation.main_activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.navigation.FinancialAssistantNavHost
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        /**Flag to prevent app from capturing screenshots and blur the app content while in background*/
        //FIXME -> Make this logic optional, user can allow or disallow
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )

        setContent {
            val viewmodel = hiltViewModel<MainViewModel>()
            val financialAssistantController = rememberNavController()

            DisposableEffect(Unit) {
                val observer = object : DefaultLifecycleObserver {

                    override fun onResume(owner: LifecycleOwner) {
                        super.onResume(owner)
                        if (viewmodel.isPinSet()) {
                            //TODO show LogIn screen
                            financialAssistantController.navigate(OnboardingRoutes.Login) {
                                popUpTo(OnboardingRoutes.Login) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }

                ProcessLifecycleOwner.get().lifecycle.addObserver(observer)

                //Dispose
                onDispose {
                    ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
                }
            }


            FinancialAssistantTheme {
                FinancialAssistantNavHost(
                    navController = financialAssistantController,
                    startDestination = viewmodel.getStartDestination()/*FinancialAssistantRoutes.Landing*/ //InsightsRoutes.Insights //viewmodel.getStartDestination()//OnboardingRoutes.Welcome
                )
            }
        }
    }

}
