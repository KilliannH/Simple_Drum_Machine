package com.google.codelabs.mdc.kotlin.shrine.models

import android.widget.ImageView
import com.google.codelabs.mdc.kotlin.shrine.R

class Beat(val imageView: ImageView?) {

    // enabled means on by user
    // active means on by player
    var enabled = false
    var active = false

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

    fun toggleActive() {
        if(active) {
            if(enabled) {
                if(this.imageView != null) {
                    this.imageView.setImageResource(R.drawable.rectangle_enabled)
                }
            } else {
                if(this.imageView != null) {
                    this.imageView.setImageResource(R.drawable.rectangle_default)
                }
            }
            this.active = false
        } else {
            if(this.imageView != null) {
                this.imageView.setImageResource(R.drawable.rectangle_active)
            }
            this.active = true
        }
    }
}