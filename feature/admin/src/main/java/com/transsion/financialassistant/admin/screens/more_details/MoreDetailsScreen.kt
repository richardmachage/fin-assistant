package com.transsion.financialassistant.admin.screens.more_details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.transsion.financialassistant.presentation.components.ThreeDotsLoader
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingMediumLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreDetailsScreen(
    navController: NavController,
    viewModel: MoreDetailsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetToast()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TitleText(text = state.title) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //description
            TitleText(text = "Description")
            NormalText(
                text = state.description, textAlign = TextAlign.Left, modifier = Modifier.padding(
                    paddingMedium
                )
            )
            VerticalSpacer(20)
            // image
            state.photoUrl?.let { imageUrl ->
                var imageIsLoading by remember { mutableStateOf(true) }
                var errorFetchingImage by remember { mutableStateOf(false) }
                Box(contentAlignment = Alignment.Center) {
                    if (imageIsLoading) {
                        ThreeDotsLoader()
                    }

                    if (errorFetchingImage) {
                        Text("Error in getting Screenshot")
                    }
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = state.description,
                        modifier = if (errorFetchingImage) Modifier else Modifier.size(500.dp),
                        onLoading = {
                            imageIsLoading = true
                        },
                        onSuccess = {
                            imageIsLoading = false
                        },
                        onError = {
                            imageIsLoading = false
                            errorFetchingImage = true
                        }
                    )
                }
            }

            // mark as resolved
            VerticalSpacer(20)
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = paddingMediumLarge, end = paddingMediumLarge),
                onClick = {
                    viewModel.onMarkSolved()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Mark as Solved")
                    HorizontalSpacer(20)
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }

        }


    }
}