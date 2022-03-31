package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.os.CountDownTimer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.models.Beat
import kotlinx.android.synthetic.main.drum_machine_fragment.view.*

/**
 * Fragment representing the login screen for Shrine.
 */
class DrumMachineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        val view = inflater.inflate(R.layout.drum_machine_fragment, container, false)

        val beats:ArrayList<Beat> = ArrayList<Beat>()

        val count:Int = view.beatsGroup.childCount

        var lastBeat: Beat? = null

        var beatIndex = 0

        // add beats from beatsGroup layout
        for (i in 0 until count) {
            beats.add(Beat(view.beatsGroup.getChildAt(i) as ImageView))
        }

        for (i in 0 until count) {
            beats[i].imageView.setOnClickListener {
                beats[i].toggleActive()
            }
        }

            fun tickCb() {
            beats[beatIndex].imageView.setImageResource(R.drawable.rectangle_playing)
            if(lastBeat !== null) {
                if(lastBeat!!.enabled) {
                    lastBeat!!.imageView.setImageResource(R.drawable.rectangle_active)
                } else {
                    lastBeat!!.imageView.setImageResource(R.drawable.rectangle_default)
                }
            }
            lastBeat = beats[beatIndex]
            if(beatIndex == 7) {
                beatIndex = 0;
            } else {
                beatIndex++
            }
        }

        // Set an error if the password is less than 8 characters.
        view.play_button.setOnClickListener {
            val timer = object: CountDownTimer(20000, 500) {
                override fun onTick(millisUntilFinished: Long) {
                    tickCb()
                }

                override fun onFinish() {Log.d("debug", "Finished!");}
            }
            timer.start()
        }

        return view
    }
}
