package com.bikotron.dinosfavouriteartists.ui.features.artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bikotron.dinosfavouriteartists.ui.composables.CommonSwipeToRefreshIndicator
import com.bikotron.domain.releases.models.Release
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ArtistScreen(
    navController: NavController,
    viewModel: ArtistViewModel,
) {
    val isRefreshing by viewModel.isRefreshing
    val artist by viewModel.artist
    val releases by viewModel.releases
    val releaseDetail by viewModel.releaseDetail
    val scaffoldState = rememberScaffoldState()
    val errorMessage by viewModel.errorMessage

    releaseDetail?.let {
        ReleaseDialog(it) { viewModel.hideReleaseDetails() }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Surface {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { viewModel.refreshReleases() },
                    modifier = Modifier.fillMaxSize(),
                    indicator = { state, trigger -> CommonSwipeToRefreshIndicator(state, trigger) }
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 32.dp),
                    ) {
                        artist?.let {
                            item {
                                Box {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(it.image)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "Artist image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(256.dp)
                                            .fillMaxWidth(),
                                    )
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            tint = Color.White,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.h3,
                                    maxLines = 10,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.profile,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                )
                                if (releases.isNotEmpty()) {
                                    Text(
                                        text = "Releases:",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(16.dp),
                                    )
                                }
                            }
                        }
                        if (releases.isEmpty()) {
                            item {
                                Text(
                                    text = "Releases are empty!",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                                )
                            }
                        } else {
                            releases.forEach { release ->
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(72.dp)
                                            .clickable {
                                                viewModel.onReleaseClick(release)
                                            }
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(release.thumb)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "Artist image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(72.dp),
                                        )
                                        Text(
                                            text = release.title,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(16.dp),
                                        )
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
        }
    )
}

@Composable
fun ReleaseDialog(release: Release, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        text = release.title,
                        style = MaterialTheme.typography.h6,
                        maxLines = 10,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                    IconButton(onClick = { onDismiss() }, modifier = Modifier.weight(0.1f)) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            tint = Color.Black,
                            contentDescription = "Back"
                        )
                    }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(release.thumb)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Artist image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                ReleaseDialogRow("Type: ", release.type)
                ReleaseDialogRow("Status: ", release.status)
                ReleaseDialogRow("Format: ", release.format)
                ReleaseDialogRow("Label: ", release.label)
                ReleaseDialogRow("Role: ", release.role)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun ReleaseDialogRow(title: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = value.orEmpty())
        }
    }
}