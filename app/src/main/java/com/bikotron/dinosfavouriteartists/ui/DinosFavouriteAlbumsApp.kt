package com.bikotron.dinosfavouriteartists.ui


import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bikotron.dinosfavouriteartists.ui.navigation.DinosFavouriteAlbumsNavGraph
import com.bikotron.dinosfavouriteartists.ui.theme.DinosFavouriteAlbumsTheme

@Composable
fun DinosFavouriteAlbumsApp() {
    DinosFavouriteAlbumsTheme {
        val navController = rememberNavController()
        DinosFavouriteAlbumsNavGraph(navController = navController)
    }
}