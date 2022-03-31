package com.google.codelabs.mdc.kotlin.shrine.timer

import android.os.CountDownTimer


abstract class CountUpTimer(countUpInterval: Long) {
    private val countDownTimer: CountDownTimer
    private var countDownCycle: Int
    fun start() {
        countDownTimer.start()
    }

    fun stop() {
        countDownTimer.cancel()
    }

    fun cancel() {
        stop()
        countDownCycle = 1
    }

    abstract fun onTick(millisElapsed: Long)

    init {
        countDownCycle = 1
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, countUpInterval) {
            override fun onTick(millisUntilFinished: Long) {
                this@CountUpTimer.onTick(Long.MAX_VALUE * countDownCycle - millisUntilFinished)
            }

            override fun onFinish() {
                countDownCycle++
                this@CountUpTimer.start()
            }
        }
    }
}