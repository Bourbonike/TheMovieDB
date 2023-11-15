package com.example.themoviedb.network.network

import com.example.themoviedb.BuildConfig
import com.example.themoviedb.network.network.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOVIE_DB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .header(
                        "Authorization",
                        "Bearer " + BuildConfig.MOVIE_DB_API_KEY
                    )

                val request: Request = requestBuilder.build()
                chain.proceed(request)

            }.build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

}