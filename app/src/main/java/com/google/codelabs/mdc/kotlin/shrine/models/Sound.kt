package com.google.codelabs.mdc.kotlin.shrine.models

import android.content.Context
import android.media.MediaPlayer

class Sound(private val soundFile: Int, val name: String, private val context: Context?) {

    private var mediaPlayer: MediaPlayer? = null

    fun load() {
        mediaPlayer = MediaPlayer.create(context, soundFile)
    }

    fun play() {
        if(mediaPlayer !== null) {
            mediaPlayer!!.start()
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}