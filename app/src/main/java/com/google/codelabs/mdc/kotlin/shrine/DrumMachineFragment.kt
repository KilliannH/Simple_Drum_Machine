package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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

        val beats:ArrayList<ImageView> = ArrayList<ImageView>()

        val count:Int = view.beatsGroup.childCount

        var lastBeat: ImageView? = null

        var beatIndex = 0

        // add beats from beatsGroup layout
        for (i in 0 until count) {
            beats.add(view.beatsGroup.getChildAt(i) as ImageView)
        }

        fun tickCb() {
            beats[beatIndex].setImageResource(R.drawable.rectangle_active)
            if(lastBeat !== null) {
                lastBeat!!.setImageResource(R.drawable.rectangle_default)
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
            val timer = object: CountDownTimer(20000, 1000) {
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
