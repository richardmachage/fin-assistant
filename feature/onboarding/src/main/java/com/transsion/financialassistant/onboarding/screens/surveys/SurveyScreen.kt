package com.transsion.financialassistant.onboarding.screens.surveys

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun SurveyScreen(
    navController: NavController
) {
    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight(0.8f),
            ) {
                Column(
                    modifier = Modifier
                        //.padding(paddingValues)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    BigTittleText(
                        text = stringResource(R.string.what_do_you_want_to_do),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = paddingSmall)
                    )

                    FaintText(
                        text = stringResource(R.string.we_use_this_information),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = paddingSmall)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SurveyScreenPreview(){
    FinancialAssistantTheme {
        SurveyScreen(navController = rememberNavController())
    }
}