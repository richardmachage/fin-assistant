package com.transsion.financialassistant.onboarding.screens.confirm_number

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.DynamicText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@Preview(showSystemUi = true)
@Composable
fun ConfirmNumberScreen(
    viewModel: ConfirmNumberViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + paddingLarge,
                    start = paddingLarge, end = paddingLarge
                ),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                //tittle
                BigTittleText(
                    text = stringResource(R.string.begin_your_smartmoney_journey)
                )
                //number selection -> different components when sims are two versus one

                DynamicText(
                    stringId = R.string.detected_one_number,
                    dynamicValue = "0712345678",
                )

                //Not Your number
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    NormalText(
                        text = stringResource(R.string.not_your_number)
                    )
                    HorizontalSpacer(5)

                    ClickableText(
                        text = stringResource(R.string.use_another_number),
                        onClick = {
                            //TODO: navigate to number selection
                        },
                        underline = true
                    )
                }

            }
            //Get started Button
            FilledButtonFa(
                //modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                text = stringResource(R.string.get_started),
                onClick = {
                    //TODO
                }
            )

            //privacy policy and terms of use

            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        //.align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FaintText(

                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.by_clicking_get_started)
                    )
                    VerticalSpacer(10)

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ClickableText(
                            text = stringResource(R.string.services_agreement),
                            onClick = {
                                //TODO
                            }
                        )
                        HorizontalSpacer(5)
                        NormalText(text = stringResource(R.string.and))
                        HorizontalSpacer(5)

                        ClickableText(
                            text = stringResource(R.string.privacy_policy),
                            onClick = {
                                //TODO
                            }
                        )
                    }
                }
            }
        }
    }
}

