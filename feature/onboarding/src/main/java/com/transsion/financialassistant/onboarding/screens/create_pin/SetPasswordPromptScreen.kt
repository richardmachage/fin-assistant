package com.transsion.financialassistant.onboarding.screens.create_pin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium

@Composable
fun SetPasswordPromptScreen(
    onSkip: () -> Unit,
    onContinue: () -> Unit
) {

    Scaffold { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            /*Row(
                modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .align(Alignment.TopCenter)
                // .padding(top = innerPadding.calculateTopPadding())
                ,
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
            }*/


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer(8)
                Column(
                    modifier = Modifier
                    // .fillMaxHeight(0.f)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(width = 200.dp, height = 153.dp),
                        painter = painterResource(id = R.drawable.glass_security_shield),
                        contentDescription = "security",
                    )

                    VerticalSpacer(32)
                    TitleText(
                        text = stringResource(R.string.log_in_securely),
                        fontSize = 24.sp,

                        )

                    VerticalSpacer(12)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = paddingLarge),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        //secure yor app
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = paddingMedium, end = paddingMedium),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.iconamoon_shield_yes),
                                contentDescription = "shield",
                                tint = FAColors.green
                            )
                            // HorizontalSpacer(6)
                            NormalText(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                text = stringResource(R.string.secure_your_app_pin),
                                textAlign = TextAlign.Start

                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(paddingLarge))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = paddingMedium, end = paddingMedium),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint),
                                contentDescription = "schedule_icon",
                                tint = FAColors.green
                            )
                            // HorizontalSpacer(6)
                            NormalText(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                text = stringResource(R.string.login_fingerprint),
                                textAlign = TextAlign.Start
                            )

                        }
                        HorizontalDivider(modifier = Modifier.padding(paddingLarge))

                        //your data does not leave your phone
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = paddingMedium, end = paddingMedium),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.database_locked),
                                contentDescription = "schedule_icon",
                                tint = FAColors.green
                            )

                            // HorizontalSpacer(6)
                            NormalText(
                                modifier = Modifier.fillMaxWidth(0.8f),
                                text = stringResource(R.string.data_doesnot_leave_phone),
                                textAlign = TextAlign.Start
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(paddingLarge))


                    }
                }

            }

            Column(
                modifier = Modifier
                    .padding(paddingMedium)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Continue Button
                FilledButtonFa(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onContinue()
                    },
                    text = stringResource(R.string.continue_btn)
                )
                VerticalSpacer(8)
                //Skip Button
                OutlineButtonFa(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        Log.d("SetPasswordPromptScreen", "Onskip clicked: ")
                        onSkip()
                    },
                    text = stringResource(R.string.skip)
                )
            }
        }
    }

}