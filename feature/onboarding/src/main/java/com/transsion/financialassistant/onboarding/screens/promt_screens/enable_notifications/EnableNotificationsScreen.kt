package com.transsion.financialassistant.onboarding.screens.promt_screens.enable_notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@Composable
fun EnableNotificationScreen(navController: NavController){
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + paddingLarge,
                    end = paddingLarge
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.End),
                painter = painterResource(id = R.drawable.frame_25),
                contentDescription = "close",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )

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
                NormalText(
                    text = stringResource(R.string.get_instant_notifications),
                    fontSize = 25.sp
                )

                VerticalSpacer(12)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = paddingLarge, end = paddingLarge),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier.size(iconSize),
                            painter = painterResource(id = R.drawable.akar_icons_schedule),
                            contentDescription = "schedule_icon"
                        )
                        HorizontalSpacer(4)
                        NormalText(
                            text = stringResource(R.string.get_reminders),
                            lineHeight = 30.sp
                        )
                    }

                    VerticalSpacer(8)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            modifier = Modifier.size(iconSize),
                            painter = painterResource(id = R.drawable.notification_02),
                            contentDescription = "notifications"
                        )
                        HorizontalSpacer(4)
                        NormalText(
                            text = stringResource(R.string.customized_notifications),
                            lineHeight = 30.sp
                        )
                    }
                }
            }

            VerticalSpacer(16)

            //Continue Button
            FilledButtonFa(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
                    //.align(Alignment.BottomCenter)
                    .imePadding(),
                onClick = {},
                text = stringResource(R.string.enable_notifications)
            )

            //Skip Button
            OutlineButtonFa(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
                    //.align(Alignment.BottomCenter)
                    .imePadding(),
                onClick = {},
                text = stringResource(R.string.do_later)
            )
        }
    }

}