package com.transsion.financialassistant.admin.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.wear.compose.material.ListHeader
import com.transsion.financialassistant.admin.model.FeedBack
import com.transsion.financialassistant.admin.navigation.AdminRoutes
import com.transsion.financialassistant.presentation.components.ThreeDotsLoader
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        when (state.isLoading) {
            true -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { ThreeDotsLoader() }

            false -> {
                if (state.feedbacks.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(paddingMedium)
                    ) {

                        item { ListHeader { Text("Issues") } }

                        items(state.feedbacks) {
                            ListItem(
                                feedBack = it,
                                onClick = {
                                    navController.navigate(
                                        AdminRoutes.MoreDetails(
                                            title = it.title,
                                            description = it.description,
                                            isSolved = false,
                                            screenShotUrl = it.attachment
                                        )
                                    )
                                }
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No Issues ...")
                    }
                }

            }
        }


    }
}


@Composable
private fun ListItem(
    feedBack: FeedBack,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingMedium)
            .clickable { onClick() }
    ) {
        VerticalSpacer(5)
        //tittle
        TitleText(text = feedBack.title, modifier = Modifier.padding(paddingSmall))
        //description
        NormalText(
            text = feedBack.description,
            modifier = Modifier.padding(paddingSmall),
            textAlign = TextAlign.Left
        )

    }
}