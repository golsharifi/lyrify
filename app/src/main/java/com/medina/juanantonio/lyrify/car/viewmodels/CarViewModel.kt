package com.medina.juanantonio.lyrify.car.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medina.juanantonio.lyrify.car.models.CarUiState 
import com.medina.juanantonio.lyrify.data.usecase.SpotifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val spotifyUseCase: SpotifyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CarUiState())
    val uiState: StateFlow<CarUiState> = _uiState.asStateFlow()

    fun updateCurrentTrack() {
        viewModelScope.launch {
            val currentTrack = spotifyUseCase.getCurrentPlayingTrack()
            currentTrack?.let { track ->
                _uiState.value = _uiState.value.copy(
                    isPlaying = track.isMusicPlaying,
                    currentSong = track.songName,
                    artist = track.artist,
                    currentProgress = track.playProgress.toLong()
                )
                
                // Update lyrics
                val lyrics = spotifyUseCase.getSongLyrics(track)
                lyrics?.lines?.map { it.words }?.let { lyricLines ->
                    _uiState.value = _uiState.value.copy(
                        lyrics = lyricLines
                    )
                }
            }
        }
    }

    fun playPause() {
        viewModelScope.launch {
            if (_uiState.value.isPlaying) {
                spotifyUseCase.pause()
            } else {
                spotifyUseCase.play()
            }
            updateCurrentTrack()
        }
    }

    fun skipNext() {
        viewModelScope.launch {
            spotifyUseCase.skipToNext()
            updateCurrentTrack()
        }
    }

    fun skipPrevious() {
        viewModelScope.launch {
            spotifyUseCase.skipToPrevious()
            updateCurrentTrack()
        }
    }
}
