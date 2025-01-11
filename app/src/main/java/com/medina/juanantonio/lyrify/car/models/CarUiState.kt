package com.medina.juanantonio.lyrify.car.models

data class CarUiState(
    val currentSong: String = "",
    val artist: String = "",
    val lyrics: List<String> = emptyList(),
    val isPlaying: Boolean = false,
    val currentProgress: Long = 0L
)
