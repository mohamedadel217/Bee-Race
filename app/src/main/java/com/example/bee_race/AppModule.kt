package com.example.bee_race

import com.example.common.DefaultDispatcherProvider
import com.example.common.DispatcherProvider
import com.example.data.BeeRaceApi
import com.example.data.BeeRaceRepositoryImpl
import com.example.domain.BeeRaceRepository
import com.example.domain.usecases.FetchBeeListUseCase
import com.example.domain.usecases.FetchRaceDurationUseCase
import com.example.feature.ui.RaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppModule {
    val appModule = module {
        single<DispatcherProvider> { DefaultDispatcherProvider() }
        single<BeeRaceApi> {
            Retrofit.Builder()
                .baseUrl("https://rtest.proxy.beeceptor.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(BeeRaceApi::class.java)
        }
        single<BeeRaceRepository> { BeeRaceRepositoryImpl(get(), get()) }
        factory { FetchRaceDurationUseCase(get()) }
        factory { FetchBeeListUseCase(get()) }
        viewModel { RaceViewModel(get(), get()) }

    }
}