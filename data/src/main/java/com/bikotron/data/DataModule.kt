package com.bikotron.data

import android.content.Context
import androidx.room.Room
import com.bikotron.data.adapter.LocalDateTimeAdapter
import com.bikotron.data.db.DinosFavouriteAlbumsDb
import com.bikotron.data.network.DiscogsApi
import com.bikotron.data.network.UserAgentInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(UserAgentInterceptor())
        .build()

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.discogs.com")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun room(@ApplicationContext applicationContext: Context): DinosFavouriteAlbumsDb = Room.databaseBuilder(
        applicationContext,
        DinosFavouriteAlbumsDb::class.java,
        "dinos-favourite-artists"
    ).build()

    @Provides
    internal fun provideDiscogsApi(retrofit: Retrofit): DiscogsApi = retrofit.create(DiscogsApi::class.java)

}
