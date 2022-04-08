package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Marker(val imageView: ImageView) {

    // active means on by player
    var active = false

    fun toggleActive() {
        if(active) {
            this.imageView.setImageResource(R.drawable.circle_default)
            this.active = false
        } else {
            this.imageView.setImageResource(R.drawable.circle_active)
            this.active = true
        }
    }
}