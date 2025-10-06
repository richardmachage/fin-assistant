package com.transsion.financialassistant.onboarding.screens.get_started

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.navigation.OnboardingRoutes
import com.transsion.financialassistant.presentation.components.dialogs.ConfirmDialog
import com.transsion.financialassistant.presentation.components.dialogs.InfoDialog
import com.transsion.financialassistant.presentation.components.isPermissionGranted
import com.transsion.financialassistant.presentation.components.requestMultiplePermissions
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingSmall
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//@Preview(showSystemUi = true)
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun GetStarted(
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isGranted = remember {
        context.isPermissionGranted(Manifest.permission.READ_PHONE_STATE) && context.isPermissionGranted(
            Manifest.permission.READ_PHONE_NUMBERS
        )

    }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }


    val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_PHONE_NUMBERS
    )
    val launcher = requestMultiplePermissions(
        onPermissionGranted = {
            isGranted = true
            showPermissionDialog = false
            showPermissionRationaleDialog = false

            navController.navigate(OnboardingRoutes.CreatePin) {
                popUpTo<OnboardingRoutes.Welcome> {
                    inclusive = true
                }
            }
        },
        onPermissionDenied = {
            showPermissionRationaleDialog = true
        }
    )

    Scaffold { innerPadding ->

        //Read phone state Permission dialog
        ConfirmDialog(
            showDialog = showPermissionDialog,
            title = stringResource(R.string.allow_financial_assistant_to_access_sim),
            message = stringResource(R.string.we_need_to_access_your_sim_info),
            onDismiss = {
                showPermissionDialog = false
                showPermissionRationaleDialog = true
            },
            onConfirm = {
                showPermissionDialog = false
                launcher.launch(permissions)
            },
            confirmButtonText = stringResource(com.transsion.financialassistant.presentation.R.string.allow),
            dismissButtonText = stringResource(R.string.deny)
        )

        //Read phone state permission rationale dialog
        InfoDialog(
            showDialog = showPermissionRationaleDialog,
            title = stringResource(R.string.allow_financial_assistant_to_access_sim),
            message = stringResource(R.string.read_phone_state_rationale),
            onDismiss = {
                showPermissionRationaleDialog = false
                launcher.launch(permissions)
            },
            buttonText = stringResource(com.transsion.financialassistant.presentation.R.string.allow)
        )



        Box(
            modifier = Modifier
                //.padding(innerPadding)
                .fillMaxSize(),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.get_started),
                contentDescription = "image",
                contentScale = ContentScale.FillBounds
            )

            //content text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .padding(
                        bottom = innerPadding.calculateBottomPadding(),
                        start = paddingLarge,
                        end = paddingLarge
                    )
                    .align(alignment = Alignment.BottomCenter)
            ) {
                //Big tittle white text
                BigTittleText(
                    text = stringResource(R.string.get_smarter_with_finances),
                    textColor = Color.White,
                    textAlign = TextAlign.Start
                )
                VerticalSpacer(5)

                //normal text
                NormalText(
                    text = stringResource(R.string.the_financial_assistant_app_lets),
                    textColor = Color.White,
                    textAlign = TextAlign.Start

                )
                VerticalSpacer(10)

                val swipeableState = rememberSwipeableState(0)

                SwipeToStartButton(
                    modifier = Modifier.fillMaxWidth(),
                    width = LocalConfiguration.current.screenWidthDp.dp,
                    onSwipeComplete = {
                        if (isGranted) {

                            navController.navigate(OnboardingRoutes.CreatePin) {
                                popUpTo<OnboardingRoutes.Welcome> {
                                    inclusive = true
                                }
                            }

                        } else {
                            scope.launch { swipeableState.snapTo(0) }
                            showPermissionDialog = true
                        }
                    },
                    swipeableState = swipeableState
                )
                VerticalSpacer(10)

            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToStartButton(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp = 55.dp,
    onSwipeComplete: () -> Unit,
    swipeableState: SwipeableState<Int>
) {
    val density = LocalDensity.current
    // Track the swipe state

    // Anchors define where the handle can rest (start -> value=0, end -> value=1)
    val anchors = remember(width) {
        // Convert Dp to pixels, then map positions to states
        mapOf(
            0f to 0,
            with(density) { (width - height).toPx() } to 1
        )
    }

    // When fully swiped (i.e., state = 1), triggers onSwipeComplete
    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwipeComplete()
            //delay(400)
            //swipeableState.snapTo(0)
        }
    }

    // Outer box is the track
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(28.dp))
            .background(FAColors.GrayBackground)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ ->
                    FractionalThreshold(0.7f)
                },
                orientation = Orientation.Horizontal
            ),
        contentAlignment = Alignment.CenterStart
    ) {

        NormalText(
            Modifier.align(Alignment.Center),
            text = stringResource(R.string.swipe_to_get_started),
            textAlign = TextAlign.Center,
            textColor = FAColors.black
        )

        // The handle (circle with an icon) that moves with the swipe
        val offsetPx = swipeableState.offset.value

        Box(
            modifier = Modifier
                .padding(paddingSmall)
                .size(height)
                .offset { IntOffset(offsetPx.roundToInt(), 0) }
                .clip(RoundedCornerShape(40))
                .background(FAColors.green),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.swipe_arrows),
                contentDescription = "Swipe Icon",
                tint = Color.White
            )
        }
    }
}