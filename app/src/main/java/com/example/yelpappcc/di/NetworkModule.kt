package com.example.yelpappcc.di

import com.example.yelpappcc.data.remote.RemoteRepositoryImpl
import com.example.yelpappcc.data.remote.RequestInterceptor
import com.example.yelpappcc.data.remote.YelpServiceApi
import com.example.yelpappcc.domain.repository.LocalRepository
import com.example.yelpappcc.domain.repository.RemoteRepository
import com.example.yelpappcc.domain.use_cases.GetBusinessReviewsByBusinessId
import com.example.yelpappcc.domain.use_cases.GetBusinessesByLocation
import com.example.yelpappcc.utils.NetworkState
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesAcronymApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): YelpServiceApi =
        Retrofit.Builder()
            .baseUrl(YelpServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(YelpServiceApi::class.java)
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesRequestInterceptor() : RequestInterceptor = RequestInterceptor()

    @Provides
    fun providesRemoteRepository(
        serviceApi: YelpServiceApi
    ) : RemoteRepository = RemoteRepositoryImpl(serviceApi)

    @Provides
    @Singleton
    fun providesGetBusinessesByLocationUseCase(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository,
        networkState: NetworkState
    ) : GetBusinessesByLocation = GetBusinessesByLocation(localRepository, remoteRepository, networkState)

    @Provides
    @Singleton
    fun providesGetReviewsByBusinessIdUseCase(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository,
        networkState: NetworkState
    ) : GetBusinessReviewsByBusinessId = GetBusinessReviewsByBusinessId(remoteRepository, localRepository, networkState)
}