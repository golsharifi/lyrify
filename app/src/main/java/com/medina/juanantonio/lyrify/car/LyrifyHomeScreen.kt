package com.medina.juanantonio.lyrify.car

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.*
import androidx.lifecycle.lifecycleScope
import com.medina.juanantonio.lyrify.car.viewmodels.CarViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LyrifyHomeScreen private constructor(
    carContext: CarContext,
    private val carViewModel: CarViewModel
) : Screen(carContext) {
    
    class Factory @Inject constructor(
        private val carViewModel: CarViewModel
    ) {
        fun create(carContext: CarContext): LyrifyHomeScreen {
            return LyrifyHomeScreen(carContext, carViewModel)
        }
    }

    init {
        lifecycleScope.launch {
            carViewModel.uiState.collectLatest {
                invalidate()
            }
        }
        carViewModel.updateCurrentTrack()
    }

    override fun onGetTemplate(): Template {
        val currentUiState = carViewModel.uiState.value

        return ListTemplate.Builder().apply {
            setTitle("Lyrify")
            setSingleList(
                ItemList.Builder().apply {
                    if (currentUiState.currentSong.isNotEmpty()) {
                        addItem(
                            Row.Builder()
                                .setTitle(currentUiState.currentSong)
                                .addText(currentUiState.artist)
                                .build()
                        )
                    }

                    currentUiState.lyrics.forEach { lyric ->
                        addItem(
                            Row.Builder()
                                .setTitle(lyric)
                                .build()
                        )
                    }
                }.build()
            )

            setHeaderAction(Action.APP_ICON)
            setActionStrip(
                ActionStrip.Builder()
                    .addAction(
                        Action.Builder()
                            .setTitle("Previous")
                            .setOnClickListener { 
                                carViewModel.skipPrevious()
                            }
                            .build()
                    )
                    .addAction(
                        Action.Builder()
                            .setTitle(if (currentUiState.isPlaying) "Pause" else "Play")
                            .setOnClickListener {
                                carViewModel.playPause()
                            }
                            .build()
                    )
                    .addAction(
                        Action.Builder()
                            .setTitle("Next")
                            .setOnClickListener {
                                carViewModel.skipNext()
                            }
                            .build()
                    )
                    .build()
            )
        }.build()
    }
}
