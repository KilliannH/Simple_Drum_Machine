package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Mixer(val enabledButtonImageView: ImageView) {

    var enabled = false
    var steps: ArrayList<Boolean> = ArrayList<Boolean>()

    fun toggleActive() {
        if(enabled) {
            this.enabledButtonImageView.setImageResource(R.drawable.mixer_default)
            this.enabled = false
        } else {
            this.enabledButtonImageView.setImageResource(R.drawable.mixer_active)
            this.enabled = true
        }
    }
}