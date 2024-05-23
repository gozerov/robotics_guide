package ru.gozerov.roboticsguide.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.gozerov.data.login.remote.LabApi
import ru.gozerov.data.login.remote.LoginApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideLabApi(): LabApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://manage.rtuitlab.dev/api/")
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
        return retrofit.create(LabApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://assemble.rtuitlab.dev/api/v1/")
            .client(client)
            .addConverterFactory(moshiConverterFactory)
            .build()
        return retrofit.create(LoginApi::class.java)
    }

}