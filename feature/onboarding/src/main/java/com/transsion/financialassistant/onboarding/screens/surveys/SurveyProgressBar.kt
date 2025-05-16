package com.transsion.financialassistant.onboarding.screens.surveys

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun SurveyProgressBar(
    surveyViewModel: SurveyViewModel
) {
    val currentStep by surveyViewModel.currentStep
    val totalSteps = 3
    //val progress = currentStep.toFloat() / totalSteps.toFloat()
    val progress by surveyViewModel.progress.observeAsState(0f)
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(paddingSmall)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(4.dp)) // Ensures a smooth look
                    .background(Color.LightGray) // Track color
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress) // Fill dynamically
                        .height(6.dp)
                        .background(FAColors.green) // Green progress color
                )
            }
        }
        HorizontalSpacer(8)

        NormalText(
            text = "$currentStep/$totalSteps",
            //fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}