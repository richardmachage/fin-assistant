package com.transsion.financialassistant.feedback.screens.feedback

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.feedback.R
import com.transsion.financialassistant.presentation.components.CircularLoading
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBackScreen(
    navController: NavController,
    viewModel: FeedBackViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val stream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(stream)
            viewModel.onAttachmentChange(attachment = bitmap.asImageBitmap(), imageUri = it)
        }
    }


    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BigTittleText(text = stringResource(R.string.submit_feedback))
                },
                navigationIcon = {
                    IconButtonFa(
                        onClick = {
                            navController.navigateUp()
                        },
                        icon = Icons.AutoMirrored.Filled.ArrowBack
                    )
                }
            )
        }
    ) { innerPadding ->

        CircularLoading(isLoading = state.isLoading, loadingMessage = "Sending feedback")

        when (state.isLoading) {
            true -> CircularLoading(isLoading = true, loadingMessage = "Sending feedback")
            false -> Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //Text(text = stringResource(R.string.submit_feedback), style = MaterialTheme.typography.headlineSmall)
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onTittleChange(it) },
                    label = { Text(stringResource(R.string.title)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        focusedIndicatorColor = FAColors.green,
                        focusedLabelColor = FAColors.green
                    )
                )

                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onDescriptionChange(it) },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 10,
                    colors = OutlinedTextFieldDefaults.colors().copy(
                        focusedIndicatorColor = FAColors.green,
                        focusedLabelColor = FAColors.green

                    )
                )

                OutlineButtonFa(
                    text = stringResource(R.string.attach_screenshot),
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                )



                FilledButtonFa(
                    enabled = state.title.isNotBlank() && state.description.isNotBlank(),
                    text = stringResource(R.string.submit),
                    onClick = {

                        state.imageUri?.let {
                            //upload image first
                            viewModel.uploadImage(
                                context = context,
                                imageUri = it,
                                onSuccess = { imageUrl ->
                                    //now submit feedback to firebase
                                    viewModel.onSubmitFeedback(
                                        imageUrl = imageUrl,
                                        onSuccess = {},
                                        onError = {}
                                    )
                                },
                                onError = { errorMessage ->
                                    viewModel.showToast(errorMessage)
                                }
                            )
                        } ?: run {
                            //submit without image
                            viewModel.onSubmitFeedback(
                                onSuccess = {},
                                onError = {}
                            )
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                )

                //attachment
                if (state.attachment != null) {
                    Image(
                        bitmap = state.attachment!!,
                        contentDescription = "Screenshot",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@PreviewLightDark
@Composable
fun FeedBackPrev() {
    FinancialAssistantTheme {
        FeedBackScreen(rememberNavController())
    }
}