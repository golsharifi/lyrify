package com.medina.juanantonio.lyrify.di

import com.medina.juanantonio.lyrify.car.LyrifyHomeScreen
import com.medina.juanantonio.lyrify.car.viewmodels.CarViewModel
import com.medina.juanantonio.lyrify.data.usecase.SpotifyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CarModule {

    @Provides
    @Singleton
    fun provideLyrifyHomeScreen(
        carViewModel: CarViewModel
    ): LyrifyHomeScreen.Factory {
        return LyrifyHomeScreen.Factory(carViewModel)
    }

    @Provides
    @Singleton
    fun provideCarViewModel(
        spotifyUseCase: SpotifyUseCase
    ): CarViewModel {
        return CarViewModel(spotifyUseCase)
    }
}
