package com.medina.juanantonio.lyrify.car

import android.content.Context
import androidx.media.session.MediaButtonReceiver
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import javax.inject.Inject

class LyrifyMediaSession @Inject constructor(
    private val context: Context
) {
    private val mediaSession = MediaSessionCompat(context, CarConstants.MEDIA_SESSION_TAG).apply {
        setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                // Handle play
            }

            override fun onPause() {
                // Handle pause
            }

            override fun onSkipToNext() {
                // Handle next
            }

            override fun onSkipToPrevious() {
                // Handle previous
            }
        })

        setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0L, 1.0f)
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                    PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                )
                .build()
        )

        isActive = true
    }

    fun release() {
        mediaSession.release()
    }
}
