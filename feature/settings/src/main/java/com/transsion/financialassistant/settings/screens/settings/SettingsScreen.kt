package com.transsion.financialassistant.settings.screens.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMediumLarge
import com.transsion.financialassistant.presentation.utils.paddingMediumSmall

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val currentTheme by viewModel.getCurrentTheme().collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { BigTittleText(text = stringResource(com.transsion.financialassistant.presentation.R.string.settings)) }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {


            //Theme
            SettingGroup(
                title = "Theme",
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30))
                            .clickable {
                                if (currentTheme != ThemeMode.DARK.name) viewModel.setThemeMode(
                                    ThemeMode.DARK
                                )//currentTheme = ThemeMode.DARK
                            }
                            .border(
                                width = 1.dp,
                                color = if (currentTheme == ThemeMode.DARK.name) FAColors.green.copy(
                                    alpha = 0.5f
                                ) else Color.Transparent,
                                shape = RoundedCornerShape(30)
                            )
                            .padding(paddingMediumSmall),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = Color.Black,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.basil_moon_solid),
                            contentDescription = "mode"
                        )
                        HorizontalSpacer(10)
                        Text("Dark")
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30))
                            .clickable {
                                if (currentTheme != ThemeMode.LIGHT.name) viewModel.setThemeMode(
                                    ThemeMode.LIGHT
                                )
                            }
                            .border(
                                width = 1.dp,
                                color = if (currentTheme == ThemeMode.LIGHT.name) FAColors.green.copy(
                                    alpha = 0.5f
                                ) else Color.Transparent,
                                shape = RoundedCornerShape(30)
                            )
                            .padding(paddingMediumSmall),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = FAColors.GrayBackground,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.basil_moon_solid),
                            contentDescription = "mode"
                        )
                        HorizontalSpacer(10)
                        Text("Light")
                    }
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(30))
                            .clickable {
                                if (currentTheme != ThemeMode.SYSTEM.name) viewModel.setThemeMode(
                                    ThemeMode.SYSTEM
                                )
                            }
                            .border(
                                width = 1.dp,
                                color = if (currentTheme == ThemeMode.SYSTEM.name) FAColors.green.copy(
                                    alpha = 0.5f
                                ) else Color.Transparent,
                                shape = RoundedCornerShape(30)
                            )
                            .padding(paddingMediumSmall),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = FAColors.green,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.basil_moon_solid),
                            contentDescription = "mode"
                        )
                        HorizontalSpacer(10)
                        Text("System")
                    }
                    /*Switch(
                        checked = false,
                        onCheckedChange = {},
                        colors = SwitchDefaults.colors().copy(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = FAColors.green
                        )
                    )*/
                }
            }

            //Security
            SettingGroup(
                title = "Security",
            ) {
                //Change Pin
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Icon(
                        tint = FAColors.green,
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.security_password),
                        contentDescription = "security password"
                    )
                    HorizontalSpacer(10)
                    Text("Change PIN")
                }

                VerticalSpacer(15)
                HorizontalDivider()
                //VerticalSpacer(10)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = FAColors.green,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint),
                            contentDescription = "security passwword"
                        )
                        HorizontalSpacer(10)
                        Text("Fingerprint Authentication")
                    }
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        colors = SwitchDefaults.colors().copy(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = FAColors.green
                        )
                    )
                }
            }

            //App Data
            SettingGroup(
                title = "App Data",
            ) {
                //Sync Recent
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = FAColors.green,
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = "Refresh"
                        )
                        HorizontalSpacer(10)
                        Text("Refresh Transactions")
                    }

                    IconButton(onClick = {
                        //TODO show info dialog
                    }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "info")
                    }
                }

                //Sync all
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint = FAColors.green,
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.sync),
                            contentDescription = "security password"
                        )
                        HorizontalSpacer(10)
                        Text("Sync All Transactions")
                    }

                    IconButton(onClick = {
                        //TODO show info dialog
                    }) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "info")
                    }
                }
            }

            //Financial Assistant

            SettingGroup(
                title = "Financial Assistant"
            ) {
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
                //Feedback
                RowButton(
                    title = "Send Feedback",
                    onClick = {},
                    icon = com.transsion.financialassistant.presentation.R.drawable.fluent_person_feedback_24_regular
                )

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
            .clickable(
                enabled = clickEnabled,
                onClick = onClick

            )

    ) {
        Icon(
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