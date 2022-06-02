package com.bikotron.dinosfavouriteartists.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import com.bikotron.dinosfavouriteartists.R
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState


@Composable
fun CommonSwipeToRefreshIndicator(
    state: SwipeRefreshState,
    trigger: Dp,
) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = trigger,
        scale = true,
        backgroundColor = colorResource(id = R.color.background),
        contentColor = colorResource(id = R.color.colorPrimary)
    )
}