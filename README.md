# Dino's favourite artists 🎵
An Android app which fetches my top 10 most listened artists on Last.fm and fetches their details from Discogs API.

## Android development

 * Written in [Kotlin](https://kotlinlang.org/)
 * UI written in [Jetpack Compose](https://developer.android.com/jetpack/compose)
 * Uses [RxJava](https://github.com/ReactiveX/RxJava)
 * Uses [Retrofit](https://square.github.io/retrofit/) for networking
 * Uses [Room](https://developer.android.com/training/data-storage/room) for local storage
 * Uses [Hilt](https://dagger.dev/hilt/) for dependency injection
 * It's a multi-module project which follows the principles of clean architecture
 * UI framework is MVVM

## Building the project
Add the following into `local.properties`, but replace them with real values from Discogs:

```
DiscosApiKey="key"
DiscosApiSecret="secret"
```

More info about how register a Discogs app can be found [here](https://www.discogs.com/settings/developers).

## UI

![The app's flow](assets/example.gif)

## Tests

### Unit tests

Tests are written for logic in each module. Examples:

 * App: [ArtistViewModelTest](https://gitlab.com/dino_bikic/dinos-favourites-artists/-/blob/master/app/src/test/java/com/bikotron/dinosfavouriteartists/ui/features/artist/ArtistViewModelTest.kt)
 * Data: [ReleasesRetrofitDataSourceTest](https://gitlab.com/dino_bikic/github-browser/-/blob/master/data/src/test/java/com/bikotron/data/features/releases/network/ReleasesRetrofitDataSourceTest.kt)
 * Domain: [ObserveArtistAndReleasesSortByYearUseCaseTest](https://gitlab.com/dino_bikic/github-browser/-/blob/master/app/src/test/java/com/bikotron/domain/artists/usecases/ObserveArtistAndReleasesSortByYearUseCaseTest.kt)

Command to run all tests:

```
./gradlew app:testDebugUnitTest data:testDebugUnitTest domain:testDebugUnitTest
```