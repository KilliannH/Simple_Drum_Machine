package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Beat(val imageView: ImageView?) {

    // enabled means on by user
    // active means on by player
    var enabled = false

    fun toggleEnabled() {
        if(enabled) {
            if(this.imageView != null) {
                this.imageView.setImageResource(R.drawable.rectangle_default)
            }
            this.enabled = false
        } else {
            if(this.imageView != null) {
                this.imageView.setImageResource(R.drawable.rectangle_enabled)
            }
            this.enabled = true
        }
    }
}