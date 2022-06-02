package com.bikotron.dinosfavouriteartists.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikotron.dinosfavouriteartists.R
import com.bikotron.dinosfavouriteartists.ui.composables.CommonSwipeToRefreshIndicator
import com.bikotron.dinosfavouriteartists.ui.navigation.DinosFavouriteAlbumsDestinations
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val isRefreshing by viewModel.isRefreshing
    val artists by viewModel.artists
    val errorMessage by viewModel.errorMessage
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Dino's most listened artists",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                    )
                },
                backgroundColor = colorResource(id = R.color.colorPrimary)
            )
        },
        content = {
            Surface {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { viewModel.refresh() },
                    modifier = Modifier.fillMaxSize(),
                    indicator = { state, trigger -> CommonSwipeToRefreshIndicator(state, trigger) }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp),
                    ) {
                        if (artists.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 256.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "Artists are empty!",
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                        artists.forEach { artist ->
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(112.dp)
                                        .clickable {
                                            navController.navigate(
                                                DinosFavouriteAlbumsDestinations
                                                    .Artist()
                                                    .buildRoute(artist.id)
                                            ) {
                                                popUpTo(DinosFavouriteAlbumsDestinations.Home.route)
                                            }
                                        }
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(artist.image)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Artist image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.size(112.dp),
                                    )
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Spacer(modifier = Modifier.size(8.dp))
                                        Text(
                                            text = artist.name,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Spacer(modifier = Modifier.size(16.dp))
                                        Text(
                                            text = "Play count: ${artist.playCount}",
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    }
                                }
                                Divider(color = Color.LightGray)
                            }
                        }
                    }
                }
            }
            if (errorMessage != null) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = errorMessage.orEmpty(),
                    )
                    viewModel.onErrorMessageDisplayed()
                }
            }
        }
    )
}