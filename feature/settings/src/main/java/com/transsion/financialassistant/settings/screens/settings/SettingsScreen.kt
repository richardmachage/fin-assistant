package com.transsion.financialassistant.settings.screens.settings

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.transsion.financialassistant.feedback.navigation.FeedbackRoutes
import com.transsion.financialassistant.presentation.components.LinearProgressBar
import com.transsion.financialassistant.presentation.components.dialogs.ConfirmDialog
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingMediumLarge
import com.transsion.financialassistant.settings.R
import com.transsion.financialassistant.settings.navigation.SettingRoutes

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val isPinSet by viewModel.isPinSet.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.refreshIsPinSet()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { BigTittleText(text = stringResource(com.transsion.financialassistant.presentation.R.string.settings)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            //Security
            SettingGroup(
                title = "Security",
            ) {
                //Change Pin
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30))
                        .clickable {
                            navController.navigate(SettingRoutes.ChangePin)
                        }
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        tint = FAColors.green,
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.security_password),
                        contentDescription = "security password"
                    )
                    HorizontalSpacer(10)
                    Text(
                        modifier = Modifier.padding(top = paddingMedium, bottom = paddingMedium),
                        text = if (isPinSet) "Change PIN" else "Set Pin"
                    )
                }

                if (isPinSet) {
                    //info Dialog
                    ConfirmDialog(
                        showDialog = viewModel.showDialog.value,
                        title = "Disable auth",
                        message = "By confirming, you will disable authentication and be required to Reset your PIN should you want to enable Authentication again",
                        confirmButtonText = "Disable",
                        onDismiss = { viewModel.showDialog.value = false },
                        onConfirm = {
                            viewModel.onDisableAuth()
                            viewModel.showDialog.value = false
                        }
                    )

                    VerticalSpacer(15)
                    HorizontalDivider()
                    //VerticalSpacer(10)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(26.dp),
                                tint = FAColors.green,
                                painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint),
                                contentDescription = "security passwword"
                            )
                            HorizontalSpacer(10)
                            Text("Disable Authentication")
                        }
                        Switch(
                            checked = true,
                            onCheckedChange = {
                                viewModel.showDialog.value = true
                            },
                            colors = SwitchDefaults.colors().copy(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = FAColors.green
                            )
                        )
                    }
                }
            }

            //App Data
            SettingGroup(
                title = "App Data",
            ) {
                VerticalSpacer(10)

                var showRefreshDialog by remember { mutableStateOf(false) }
                var showSyncAllDialog by remember { mutableStateOf(false) }

                //Refresh Recent
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30))
                        .clickable {
                            showRefreshDialog = true
                        }
                ) {

                    // dialog
                    ConfirmDialog(
                        title = stringResource(R.string.refresh_transactions),
                        showDialog = showRefreshDialog,
                        message = "This will sync only the messages received since your previous sync",
                        confirmButtonText = stringResource(com.transsion.financialassistant.presentation.R.string.okay),
                        onDismiss = { showRefreshDialog = false },
                        onConfirm = {
                            //TODO perform sync
                            showRefreshDialog = false
                            viewModel.onRefreshTransactions(
                                onComplete = {
                                    Toast.makeText(context, "Refresh Complete", Toast.LENGTH_SHORT)
                                        .show()

                                }
                            )
                        },
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            tint = FAColors.green,
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh"
                        )
                        HorizontalSpacer(10)
                        Text("Refresh Transactions")
                    }

                }

                //Refresh Progress
                AnimatedVisibility(visible = state.showRefreshLoading) {
                    LinearProgressBar(
                        progress = viewModel.refreshProgress.collectAsState().value
                    )
                }

                VerticalSpacer(20)

                //Sync all
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30))
                        .clickable {
                            showSyncAllDialog = true
                        }
                ) {

                    // dialog
                    ConfirmDialog(
                        title = stringResource(R.string.sync_all_transactions),
                        showDialog = showSyncAllDialog,
                        message = "This action will repeat the whole process of reading transactions from the MPESA messages.\nAre you sure you want to perform this long running process?",
                        confirmButtonText = stringResource(com.transsion.financialassistant.presentation.R.string.okay),
                        onDismiss = { showSyncAllDialog = false },
                        onConfirm = {
                            //TODO perform sync
                            showSyncAllDialog = false
                            viewModel.onSyncAllTransactions(
                                onComplete = {
                                    Toast.makeText(context, "Sync Complete", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        },
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(23.dp),
                            tint = FAColors.green,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.sync),
                            contentDescription = "sync all"
                        )
                        HorizontalSpacer(10)
                        Text("Sync All Transactions")
                    }

                    /*IconButton(onClick = {
                        //TODO show info dialog
                    }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "info")
                    }*/
                }

                //Sync All Progress
                AnimatedVisibility(visible = state.showSyncAllLoading) {
                    LinearProgressBar(
                        progress = viewModel.syncAllProgress.collectAsState().value
                    )
                }
                VerticalSpacer(10)
            }


            //Financial Assistant
            SettingGroup(
                title = "Financial Assistant"
            ) {
                //Feedback
                RowButton(
                    title = "Send Feedback",
                    onClick = {
                        navController.navigate(FeedbackRoutes.Feedback)
                    },
                    icon = com.transsion.financialassistant.presentation.R.drawable.fluent_person_feedback_24_regular
                )

                VerticalSpacer(20)

                //About
                RowButton(
                    title = "About",
                    onClick = {},
                    icon = com.transsion.financialassistant.presentation.R.drawable.iconoir_app_notification
                )
                VerticalSpacer(20)
                //Privacy policy
                RowButton(
                    title = "Privacy Policy",
                    onClick = {},
                    icon = com.transsion.financialassistant.presentation.R.drawable.legal_document_01
                )

                //Terms of service

                VerticalSpacer(20)

                //version
                RowButton(
                    title = "Version 1.0.0",
                    onClick = {},
                    clickEnabled = false,
                    icon = com.transsion.financialassistant.presentation.R.drawable.downloadsimple
                )
            }

        }

    }

}


@Preview(showSystemUi = true)
@Composable
fun SettingGroup(
    modifier: Modifier = Modifier,
    title: String = "Theme",
    content: @Composable () -> Unit = {
        Column {
            repeat(3) {
                Row(Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.sync),
                        null
                    )
                    HorizontalSpacer(10)
                    Text("Theme Mode")
                }

                VerticalSpacer(15)
            }
        }
    }
) {
    Column(
        modifier = modifier
            .padding(paddingLarge)
            .fillMaxWidth()
    ) {

        TitleText(
            text = title,
        )
        VerticalSpacer(10)

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
            //.padding(paddingMedium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMediumLarge)
            ) {
                content()
            }

        }
    }
}


@Composable
fun RowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    clickEnabled: Boolean = true,
    title: String,
    @DrawableRes icon: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .clickable(
                enabled = clickEnabled,
                onClick = onClick

            )

    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            tint = FAColors.green,
            painter = painterResource(icon),
            contentDescription = title
        )
        HorizontalSpacer(10)
        Text(title)
    }
}


enum class ThemeMode {
    DARK,
    LIGHT,
    SYSTEM
}