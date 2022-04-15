package com.google.codelabs.mdc.kotlin.shrine

import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.models.Beat
import com.google.codelabs.mdc.kotlin.shrine.models.Marker
import com.google.codelabs.mdc.kotlin.shrine.models.Mixer
import com.google.codelabs.mdc.kotlin.shrine.models.Sound
import com.google.codelabs.mdc.kotlin.shrine.timer.CountUpTimer
import kotlinx.android.synthetic.main.drum_machine_fragment.view.*
import me.angrybyte.circularslider.CircularSlider

/**
 * Fragment representing the login screen for Shrine.
 */
class DrumMachineFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // Snippet from "Navigate to the next Fragment" section goes here.

        // default values are fine (usage media, max streams 1)
        val mSoundPoolBuilder = SoundPool.Builder()
        mSoundPoolBuilder.setMaxStreams(3)
        val mSoundPool = mSoundPoolBuilder.build()

        val sounds = ArrayList<Sound>()
        sounds.add(Sound(R.raw.bd, 20, mSoundPool, context))
        sounds.add(Sound(R.raw.sd, 2, mSoundPool, context))
        sounds.add(Sound(R.raw.ch, 0, mSoundPool, context))

        val view = inflater.inflate(R.layout.drum_machine_fragment, container, false)

        var isPlaying = false

        val beats:ArrayList<Beat> = ArrayList<Beat>()
        val markers:ArrayList<Marker> = ArrayList<Marker>()
        val mixers:ArrayList<Mixer> = ArrayList<Mixer>()

        val beatsCount:Int = view.beatsGroup.childCount
        val mixersCount:Int = view.mixersGroup.childCount

        var lastMarker: Marker? = null

        var markerIndex = 0

        // add beats from beatsGroup layout
        for (i in 0 until beatsCount) {
            beats.add(Beat(view.beatsGroup.getChildAt(i) as ImageView))
        }

        // add markers from markersGroup layout
        for (i in 0 until beatsCount) {
            markers.add(Marker(view.markersGroup.getChildAt(i) as ImageView))
        }

        // add mixers from mixersGroup layout
        for(i in 0 until mixersCount) {
            val mixerLayout: LinearLayout = view.mixersGroup.getChildAt(i) as LinearLayout
            mixers.add(Mixer(mixerLayout.getChildAt(0) as CircularSlider, mixerLayout.getChildAt(1) as ImageView, sounds[i]))
        }

        // enable the first mixer by default
        mixers[0].toggleEnabled()
        var activeMixer: Mixer = mixers[0]

        for (i in 0 until beatsCount) {
            beats[i].imageView?.setOnClickListener {
                beats[i].toggleEnabled()
                activeMixer.beats[i].enabled = !activeMixer.beats[i].enabled

                // logs to see if original mixer has effectively changed, and it has.
                // Log.d("mixerrrrrs", mixers.map { i -> i.steps }.toString())
                // Log.d("active mixerrrrr", activeMixer.steps.toString())
            }
        }

        fun tickCb() {
            // --- bug steps need to beats, not only booleans bcse they will have to update their active state.
            // keep beats array to main independant visuals
            // make imageView optionable on beats

            markers[markerIndex].toggleActive()
            for (mixer: Mixer in mixers) {
                if(mixer.beats[markerIndex].enabled) {
                    mixer.sound.play()
                }
            }
            if(lastMarker !== null) {
                lastMarker!!.toggleActive()
            }
            lastMarker = markers[markerIndex]
            if(markerIndex == 7) {
                markerIndex = 0
            } else {
                markerIndex++
            }
        }

        val timer = object : CountUpTimer(250) {
            override fun onTick(millisElapsed: Long) {
                tickCb()
            }
        }

        fun updateBeats() {
            for (i in 0 until beats.size) {
                if(activeMixer.beats[i].enabled) {
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
            for(i in 0 until beatsCount) {
                if(markers[i].active) {
                    markers[i].toggleActive()
                }
            }
            lastMarker = null
            markerIndex = 0
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

            mixers[i].circularSlider.setOnSliderMovedListener {
                Log.d("debuggg", it.toString())
            }

            // init steps of each mixers to false
            for(j in 0 until beatsCount) {
                mixers[i].beats.add(Beat(null))
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
                resetBeats()
            }
        }

        return view
    }
}
