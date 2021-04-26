package com.agustin.sample.di

import android.app.Application
import com.agustin.sample.coroutines.DispatcherProvider
import com.agustin.sample.coroutines.DispatcherProviderImpl
import com.agustin.sample.network.ApiService
import com.agustin.sample.network.NetworkingManager
import com.agustin.sample.repos.MainRepository
import com.agustin.sample.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.dsl.module

@KoinExperimentalAPI
fun Application.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(
            listOf(
                repositoryModule(),
                viewModelsModule(),
                networkModule(),
            )
        )
    }
}

private fun networkModule() = module {
    single {
        NetworkingManager(get(), get())
    }
    single<ApiService> {
        get<NetworkingManager>().retrofitInstance.create(ApiService::class.java)
    }
}

private fun repositoryModule() = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }
    single { MainRepository(get(), get())}
}

private fun viewModelsModule() = module {
    viewModel { MainViewModel(get()) }
}
