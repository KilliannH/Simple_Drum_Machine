package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.models.Beat
import com.google.codelabs.mdc.kotlin.shrine.models.Mixer
import com.google.codelabs.mdc.kotlin.shrine.timer.CountUpTimer
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

        var isPlaying = false

        val beats:ArrayList<Beat> = ArrayList<Beat>()
        val mixers:ArrayList<Mixer> = ArrayList<Mixer>()

        val beatsCount:Int = view.beatsGroup.childCount
        val mixersCount:Int = view.mixersGroup.childCount

        var lastBeat: Beat? = null

        var beatIndex = 0

        // add beats from beatsGroup layout
        for (i in 0 until beatsCount) {
            beats.add(Beat(view.beatsGroup.getChildAt(i) as ImageView))
        }

        // add mixers from mixersGroup layout
        for(i in 0 until mixersCount) {
            val mixerLayout: LinearLayout = view.mixersGroup.getChildAt(i) as LinearLayout
            mixers.add(Mixer(mixerLayout.getChildAt(1) as ImageView))
        }

        // enable the first mixer by default
        mixers[0].toggleEnabled()
        var activeMixer: Mixer = mixers[0]

        for (i in 0 until beatsCount) {
            beats[i].imageView.setOnClickListener {
                beats[i].toggleEnabled()
                activeMixer.steps[i] = !activeMixer.steps[i]

                // logs to see if original mixer has effectively changed, and it has.
                // Log.d("mixerrrrrs", mixers.map { i -> i.steps }.toString())
                // Log.d("active mixerrrrr", activeMixer.steps.toString())
            }
        }

        fun tickCb() {
            // --- bug steps need to beats, not only booleans bcse they will have to update their active state.
            beats[beatIndex].toggleActive()
            if(lastBeat !== null) {
                lastBeat!!.toggleActive()
                if(lastBeat!!.enabled) {
                    lastBeat!!.imageView.setImageResource(R.drawable.rectangle_enabled)
                }
            }
            lastBeat = beats[beatIndex]
            if(beatIndex == 7) {
                beatIndex = 0;
            } else {
                beatIndex++
            }
        }

        val timer = object : CountUpTimer(500) {
            override fun onTick(millisElapsed: Long) {
                tickCb()
            }
        }

        fun updateBeats() {
            for (i in 0 until beats.size) {
                if(activeMixer.steps[i]) {
                    if(!beats[i].enabled) {
                        beats[i].toggleEnabled()
                    }
                } else {
                    if(beats[i].enabled) {
                        beats[i].toggleEnabled()
                    }
                }
            }
        }

        fun resetBeats() {
            for (i in 0 until beats.size) {
                /*beats[i].active = false
                if(beats[i].enabled) {
                    beats[i].imageView.setImageResource(R.drawable.rectangle_enabled)
                } else {
                    beats[i].imageView.setImageResource(R.drawable.rectangle_default)
                }*/
                if(beats[i].active) {
                    beats[i].toggleActive()
                }
            }
            lastBeat = null
            beatIndex = 0
        }

        for (i in 0 until mixersCount) {
            mixers[i].enabledButtonImageView.setOnClickListener {
                if(!mixers[i].enabled) {
                    mixers[i].toggleEnabled()
                    activeMixer.toggleEnabled()
                    activeMixer = mixers[i]
                    updateBeats()
                }
            }

            // init steps of each mixers to false
            for(j in 0 until beatsCount) {
                mixers[i].steps.add(false)
            }
        }

        view.play_button.setOnClickListener {
            isPlaying = !isPlaying
            if(isPlaying) {
                view.play_button.setText(R.string.stop_button)
                timer.start()
            } else {
                view.play_button.setText(R.string.play_button)
                timer.stop()
                // do smthg to reset the timer & current beat index & unpaint active beat (red)
                resetBeats()
            }
        }

        return view
    }
}
