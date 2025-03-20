package com.transsion.financialassistant.onboarding.screens.promt_screens.enable_notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.CancelButton
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.InfoIconButton
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun EnableNotificationScreen(navController: NavController){
    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.9F)
                    .align(Alignment.TopCenter)
                    .padding(top = paddingValues.calculateTopPadding()),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                InfoIconButton(
                    onClick = {}
                )

                CancelButton(
                    onClick = {

                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer(8)
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val iconSize = 24.dp
                    Image(
                        modifier = Modifier
                            .size(width = 200.dp, height = 153.dp),
                        painter = painterResource(id = R.drawable.smartphone_notifications_stacked_on_top_of_each_other),
                        contentDescription = "close",
                    )

                    VerticalSpacer(32)
                    TitleText(
                        text = stringResource(R.string.get_instant_notifications),
                        fontSize = 24.sp
                    )

                    VerticalSpacer(12)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = paddingLarge),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = paddingMedium, end = paddingMedium),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.akar_icons_schedule),
                                contentDescription = "schedule_icon",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            // HorizontalSpacer(6)
                            NormalText(
                                text = stringResource(R.string.get_reminders),
                                textAlign = TextAlign.Start

                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(paddingLarge))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = paddingMedium, end = paddingMedium),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.notification_02),
                                contentDescription = "schedule_icon",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            // HorizontalSpacer(6)
                            NormalText(
                                text = stringResource(R.string.customized_notifications),
                                textAlign = TextAlign.Start
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(paddingLarge))


                        /* VerticalSpacer(8)

                        Row(modifier = Modifier.fillMaxWidth().padding(paddingSmall)) {
                            Icon(
                                painter = painterResource(id = R.drawable.notification_02),
                                contentDescription = "notifications",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            HorizontalSpacer(6)
                            NormalText(
                                text = stringResource(R.string.customized_notifications),
                                lineHeight = 30.sp
                            )
                        }
                    }*/
                    }
                }

                VerticalSpacer(16)


            }

            Column(
                modifier = Modifier
                    .padding(paddingLarge)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Continue Button
                FilledButtonFa(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    onClick = {},
                    text = stringResource(R.string.enable_notifications)
                )
                VerticalSpacer(8)
                //Skip Button
                OutlineButtonFa(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {},
                    text = stringResource(R.string.do_later)
                )
            }
        }
    }

}