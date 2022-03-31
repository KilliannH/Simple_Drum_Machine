package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Beat(val imageView: ImageView) {

    var enabled = false

    fun toggleActive() {
        if(enabled) {
            this.imageView.setImageResource(R.drawable.rectangle_default)
            this.enabled = false
        } else {
            this.imageView.setImageResource(R.drawable.rectangle_active)
            this.enabled = true
        }
    }
}