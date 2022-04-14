package com.google.codelabs.mdc.kotlin.shrine.models

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool

class Sound(private val soundFile: Int, val soundPool: SoundPool, private val context: Context?) {

    private var soundId: Int

    init {
        soundId = load()
    }

    fun load(): Int {
        return soundPool.load(context, soundFile, 0)
    }

    fun getSoundId(): Int {
        return soundId
    }

    fun play() {
        soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1.0F)
    }

    fun unload(): Boolean {
        return soundPool.unload(soundId)
    }
}