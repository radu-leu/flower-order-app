package com.leu.radu.flowerorder

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.leu.radu.flowerorder.data.ApiService
import com.leu.radu.flowerorder.screen.MainViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val retrofit: Retrofit = createNetworkClient()
private val apiService: ApiService = retrofit.create(ApiService::class.java)

val viewModelModule: Module = module {
    viewModel { MainViewModel(apiService = get()) }
}

val apiModule: Module = module {
    single { apiService }
}

private fun createNetworkClient(): Retrofit {
    val adapterFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    val gsonConverter = GsonConverterFactory.create(GsonBuilder().create())

    return Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addCallAdapterFactory(adapterFactory)
        .addConverterFactory(gsonConverter)
        .client(createOkHttpClient())
        .build()
}

private fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(createHttpLoginInterceptor())
        .readTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS)
        .build()

private fun createHttpLoginInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return interceptor
}