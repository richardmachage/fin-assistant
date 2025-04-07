package com.transsion.financialassistant.insights.screens.category_insights

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.transsion.financialassistant.presentation.components.texts.NormalText

@Composable
fun CategoryInsightsScreen(
    navController: NavController,
    viewModel: CategoryInsightsViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            NormalText(text = viewModel.category ?: "No Category")
            NormalText(text = viewModel.startDate ?: "No Category")
            NormalText(text = viewModel.endDate ?: "No Category")

        }
    }

}