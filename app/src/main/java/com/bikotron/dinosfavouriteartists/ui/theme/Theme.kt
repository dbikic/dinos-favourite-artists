package com.bikotron.dinosfavouriteartists.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bikotron.dinosfavouriteartists.R

val lightColors = lightColors(
    primary = Color(R.color.colorPrimary),
    secondary = Color(R.color.textColorSecondary),
)

@Composable
fun DinosFavouriteAlbumsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = lightColors,
        content = content
    )
}