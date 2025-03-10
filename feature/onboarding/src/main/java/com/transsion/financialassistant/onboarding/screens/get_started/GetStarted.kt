package com.transsion.financialassistant.onboarding.screens.get_started

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import kotlin.math.roundToInt

//@Preview(showSystemUi = true)
@Composable
fun GetStarted() {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                //.padding(innerPadding)
                .fillMaxSize(),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.get_started),
                contentDescription = "image"
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
                //swipe button
                /* FilledButtonFa(
                     text = stringResource(R.string.swipe_to_get_started)
                 )
                 */

                SwipeToStartButton(
                    modifier = Modifier.fillMaxWidth(),
                    width = LocalConfiguration.current.screenWidthDp.dp,
                    onSwipeComplete = {
                        //navigate to assigning accounts
                    }
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
    width: Dp,//= 300.dp,
    height: Dp = 56.dp,
    onSwipeComplete: () -> Unit
) {
    val density = LocalDensity.current
    // Track the swipe state
    /*val swipeableState = remember {
        androidx.compose.foundation.gestures.rememberSwipeableState(initialValue = 0)
    }*/
    val swipeableState = rememberSwipeableState(0)

    // Anchors define where the handle can rest (start -> value=0, end -> value=1)
    val anchors = remember(width) {
        // Convert Dp to pixels, then map positions to states
        mapOf(
            0f to 0,
            with(density) { (width - height).toPx() } to 1
        )
    }

    // When fully swiped (i.e., state = 1), trigger onSwipeComplete
    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwipeComplete()
        }
    }

    // Outer box is the track
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFC8E6C9)) // Light green-ish background
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ ->
                    //androidx.compose.foundation.gestures.FractionalThreshold(0.5f)
                    FractionalThreshold(0.5f)
                },
                orientation = Orientation.Horizontal
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        // "Swipe to get started" text in the center
        Text(
            text = "Swipe to get started",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.Center)
        )

        // The handle (circle with an icon) that moves with the swipe
        val offsetPx = swipeableState.offset.value

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetPx.roundToInt(), 0) }
                .size(height) // same size as the track height, so it's a circle
                .clip(CircleShape)
                .background(Color(0xFF4CAF50)) // Green handle
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Replace with your own icon; using built-in arrow icons or a custom resource
            Icon(
                painter = painterResource(id = android.R.drawable.ic_media_ff),
                contentDescription = "Swipe Icon",
                tint = Color.White
            )
        }
    }
}