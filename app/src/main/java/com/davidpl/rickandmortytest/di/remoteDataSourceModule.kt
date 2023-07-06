package com.davidpl.rickandmortytest.di

import com.davidpl.rickandmortytest.datasource.remote.RemoteCharactersDataSource
import com.davidpl.rickandmortytest.datasource.remote.CharactersApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal const val REMOTE_TARGETS_DATA_SOURCE = "RemoteTargetsDataSource"
internal const val API_URL = "https://rickandmortyapi.com/"

var remoteDataSourceModule = module {

    single(named(REMOTE_TARGETS_DATA_SOURCE)) {
        RemoteCharactersDataSource(targetsApiService())
    }

}

private fun targetsApiService(): CharactersApiService {
    return retrofit().create(CharactersApiService::class.java)
}

private fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp())
        .build()
}

private fun okHttp(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor) //httpLoggingInterceptor allows to log json body.
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}