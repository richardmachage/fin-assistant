package com.transsion.financialassistant.presentation.landing_screen

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.R
import com.transsion.financialassistant.feedback.navigation.FeedbackRoutes
import com.transsion.financialassistant.home.navigation.HomeRoutes
import com.transsion.financialassistant.home.screens.home.HomeScreen
import com.transsion.financialassistant.insights.navigation.InsightsRoutes
import com.transsion.financialassistant.insights.screens.insights.InsightsScreen
import com.transsion.financialassistant.presentation.components.bottom_nav_bar.BottomBarItem
import com.transsion.financialassistant.presentation.components.bottom_nav_bar.BottomNavBar
import com.transsion.financialassistant.presentation.components.dialogs.ConfirmDialog
import com.transsion.financialassistant.presentation.components.dialogs.InfoDialog
import com.transsion.financialassistant.presentation.components.isPermissionGranted
import com.transsion.financialassistant.presentation.components.requestMultiplePermissions
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium

@Composable
fun LandingScreen(
    mainNavController: NavController,
    viewModel: LandingViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var isMessagesRead by remember {
        mutableStateOf(
            viewModel.isMessagesRead()
        )
    }


    var isGranted by remember {
        mutableStateOf(
            context.isPermissionGranted(Manifest.permission.READ_SMS)
        )
    }

    when (isGranted) {
        true -> {
            if (isMessagesRead) {
                //go on with usual app flow
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 110.dp),
                            navController = navController,
                            listOfBottomBarItems = listOf(
                                BottomBarItem(
                                    route = HomeRoutes.Home,
                                    title = R.string.home,
                                    icon = com.transsion.financialassistant.presentation.R.drawable.home_11
                                ),
                                BottomBarItem(
                                    route = InsightsRoutes.Insights,
                                    title = com.transsion.financialassistant.insights.R.string.insights,
                                    icon = com.transsion.financialassistant.presentation.R.drawable.chartpieslice
                                ),

                                )
                        )
                    }
                ) { innerPadding ->

                    NavHost(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        navController = navController,
                        startDestination = HomeRoutes.Home
                    ) {


                    composable<InsightsRoutes.Insights>(
                        ) {
                            InsightsScreen(
                                navController = mainNavController
                            )
                        }

                        composable<HomeRoutes.Home>(

                        ) {
                            HomeScreen(
                                navController = mainNavController,
                                goToFeedBack = {
                                    mainNavController.navigate(FeedbackRoutes.Feedback)
                                }
                            )
                        }

                    }
                }
            } else {
                //show UI for Loading the messages first
                viewModel.readMessagesAndSave(
                    onFinish = {
                        if (it == "Success") {
                            isMessagesRead = true
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to load transactions please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val progress by viewModel.progress.collectAsState()

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(15)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    //circular progress bar
                                    //-> to be shown when process state is loading
                                    VerticalSpacer(10)
                                    AnimatedVisibility(
                                        modifier = Modifier.fillMaxWidth(),
                                        visible = progress == 0F
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator(
                                                color = FAColors.green,
                                                strokeWidth = 2.dp
                                            )
                                        }

                                    }
                                    AnimatedVisibility(
                                        modifier = Modifier.fillMaxWidth(),
                                        visible = progress != 0F
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(paddingMedium),
                                        ) {
                                            LinearProgressIndicator(
                                                modifier = Modifier
                                                    .padding(paddingMedium)
                                                    .fillMaxWidth(),
                                                progress = { progress },
                                                color = FAColors.green,
                                                trackColor = FAColors.lightGreen,
                                                drawStopIndicator = {},
                                            )
                                            NormalText(
                                                fontWeight = FontWeight.Bold,
                                                text = "${(progress * 100).toInt()}%",
                                                fontSize = 16.sp
                                            )

                                        }
                                    }
                                    VerticalSpacer(5)
                                    //Loading transactions... text
                                    TitleText(
                                        text = stringResource(R.string.loading_transactions)
                                    )
                                    VerticalSpacer(10)
                                }
                            }
                        }
                    }
                }
            }

        }

        false -> {
            var showPermissionDialog by remember { mutableStateOf(false) }
            var showPermissionRationaleDialog by remember { mutableStateOf(false) }

            val permission = arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS
            )

            val launcher = requestMultiplePermissions(
                onPermissionGranted = {
                    isGranted = true
                    showPermissionDialog = false
                    showPermissionRationaleDialog = false
                },
                onPermissionDenied = {
                    showPermissionRationaleDialog = true
                }
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                // Read SMS Permission Dialog
                ConfirmDialog(
                    showDialog = showPermissionDialog,
                    title = stringResource(com.transsion.financialassistant.onboarding.R.string.sms_permission_title),
                    message = stringResource(com.transsion.financialassistant.onboarding.R.string.sms_permission_message),
                    onDismiss = {
                        showPermissionDialog = false
                        showPermissionRationaleDialog = true
                    },
                    onConfirm = {
                        showPermissionDialog = false
                        launcher.launch(permission)
                    },
                    confirmButtonText = stringResource(com.transsion.financialassistant.onboarding.R.string.allow),
                    dismissButtonText = stringResource(com.transsion.financialassistant.onboarding.R.string.deny)
                )

                // Read sms permission rationale dialog
                InfoDialog(
                    showDialog = showPermissionRationaleDialog,
                    title = stringResource(com.transsion.financialassistant.onboarding.R.string.sms_permission_title),
                    message = stringResource(com.transsion.financialassistant.onboarding.R.string.sms_permission_rationale),
                    onDismiss = {
                        showPermissionRationaleDialog = false
                        launcher.launch(permission)
                    },
                    buttonText = stringResource(com.transsion.financialassistant.onboarding.R.string.allow)
                )

                ClickableText(
                    onClick = { showPermissionDialog = true },
                    text = "Please allow permission to read transactions"
                )
            }
        }
    }


    viewModel.enqueUnknownMessagesWorker()
}
