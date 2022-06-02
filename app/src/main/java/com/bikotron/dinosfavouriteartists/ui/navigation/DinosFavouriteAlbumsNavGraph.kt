package com.bikotron.dinosfavouriteartists.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bikotron.dinosfavouriteartists.ui.features.artist.ArtistScreen
import com.bikotron.dinosfavouriteartists.ui.features.artist.ArtistViewModel
import com.bikotron.dinosfavouriteartists.ui.features.home.HomeScreen
import com.bikotron.dinosfavouriteartists.ui.features.splash.SplashScreen

sealed class DinosFavouriteAlbumsDestinations(val route: String) {
    object Splash : DinosFavouriteAlbumsDestinations("splash")
    object Home : DinosFavouriteAlbumsDestinations("home")
    class Artist : DinosFavouriteAlbumsDestinations("artist/{$ARTIST_ID}") {

        fun buildRoute(artistId: String): String = "artist/$artistId"

        companion object {
            const val ARTIST_ID = "artistId"
        }
    }
}

@Composable
fun DinosFavouriteAlbumsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = DinosFavouriteAlbumsDestinations.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(DinosFavouriteAlbumsDestinations.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(DinosFavouriteAlbumsDestinations.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = hiltViewModel(),
            )
        }
        composable(
            DinosFavouriteAlbumsDestinations.Artist().route,
            arguments = listOf(
                navArgument(DinosFavouriteAlbumsDestinations.Artist.ARTIST_ID) {
                    type = NavType.StringType
                }
            ),
        ) { backStackEntry ->
            val viewModel = hiltViewModel<ArtistViewModel>()
            val artistId = backStackEntry.arguments?.getString(DinosFavouriteAlbumsDestinations.Artist.ARTIST_ID).orEmpty()
            viewModel.initialize(artistId)
            ArtistScreen(
                navController = navController,
                viewModel = hiltViewModel(),
            )
        }
    }
}
