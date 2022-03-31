package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Mixer(val enableButtonImageView: ImageView) {

    var enabled = false

    fun toggleActive() {
        if(enabled) {
            this.enableButtonImageView.setImageResource(R.drawable.mixer_default)
            this.enabled = false
        } else {
            this.enableButtonImageView.setImageResource(R.drawable.mixer_active)
            this.enabled = true
        }
    }
}