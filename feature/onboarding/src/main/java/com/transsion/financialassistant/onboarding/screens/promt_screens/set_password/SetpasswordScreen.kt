package com.transsion.financialassistant.onboarding.screens.promt_screens.set_password

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.R.drawable.stash_question
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium

@Composable
fun SetPasswordScreen(
    navController: NavController
){
    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        )

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding() + paddingLarge,
                    start = paddingLarge, end = paddingLarge
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Image(
                   painter = painterResource(id = stash_question),
                   contentDescription = "help",
                   colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
               )
                Image(
                    painter = painterResource(id = R.drawable.iconoir_cancel),
                    contentDescription = "close",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }

            VerticalSpacer(32)
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.glass_security_shield),
                    contentDescription = "help"
                )
                NormalText(
                    modifier = Modifier.padding(paddingMedium),
                    text = stringResource(R.string.log_in_securely),
                    fontSize = 25.sp,
                    lineHeight = 30.sp
                )

                VerticalSpacer(12)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = paddingLarge, end = paddingLarge),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val iconSize = 24.dp
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = R.drawable.iconamoon_shield_yes),
                        contentDescription = "pin_icon"
                    )
                    HorizontalSpacer(4)
                    NormalText(
                        text = stringResource(R.string.secure_your_app_pin),
                        lineHeight = 30.sp
                    )
                }
                VerticalSpacer(8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            modifier = Modifier.size(iconSize),
                            painter = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint),
                            contentDescription = "help"
                        )
                        HorizontalSpacer(4)
                        NormalText(
                            text = stringResource(R.string.login_fingerprint),
                            lineHeight = 30.sp
                        )
                    }
                    VerticalSpacer(8)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            modifier = Modifier.size(iconSize),
                            painter = painterResource(id = R.drawable.database_locked),
                            contentDescription = "db_icon"
                        )
                        HorizontalSpacer(4)
                        NormalText(
                            text = stringResource(R.string.data_doesnot_leave_phone),
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
                onClick = {navController.navigate(OnboardingRoutes.EnableNotifications)},
                text = stringResource(R.string.continue_btn)
            )

            //Skip Button
            OutlineButtonFa(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
                    //.align(Alignment.BottomCenter)
                    .imePadding(),
                onClick = {},
                text = stringResource(R.string.skip)
            )
        }
    }
}