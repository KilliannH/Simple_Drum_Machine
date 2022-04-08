package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R
import me.angrybyte.circularslider.CircularSlider

class Mixer(val circularSlider: CircularSlider, val enabledButtonImageView: ImageView, var sound :Sound) {

    var enabled = false
    var beats: ArrayList<Beat> = ArrayList<Beat>()

    init {
        sound.load()
    }

    fun toggleEnabled() {
        if(enabled) {
            this.enabledButtonImageView.setImageResource(R.drawable.mixer_default)
            this.enabled = false
        } else {
            this.enabledButtonImageView.setImageResource(R.drawable.mixer_enabled)
            this.enabled = true
        }
    }
}