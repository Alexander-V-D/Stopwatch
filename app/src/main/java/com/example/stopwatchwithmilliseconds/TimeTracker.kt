package com.example.stopwatchwithmilliseconds

import android.os.Handler
import android.os.SystemClock
import android.widget.TextView
import java.util.*

class TimeTracker(timeView: TextView) {
    var running = false
    val handler = Handler()
    var timeAtStop = 0L
    var wasRunning = false
    val mTimeView = timeView
    var base = SystemClock.elapsedRealtime()
    private val runnable: Runnable = object: Runnable {
        override fun run() {
            if(running) mTimeView.text = calcTime()
            handler.postDelayed(this, 0)
        }
    }
    fun stop() {
        running = false
        timeAtStop = SystemClock.elapsedRealtime()
    }
    fun start() {
        if(wasRunning) {
            base += SystemClock.elapsedRealtime() - timeAtStop
        } else {
            base  = SystemClock.elapsedRealtime()
            wasRunning = true
        }
        running = true
    }
    fun reset() {
        running = false
        wasRunning = false
        base = SystemClock.elapsedRealtime()
        mTimeView.text = calcTime()
    }
    fun calcTime(): String {
        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            getTime() / 60_000, getTime() % 60_000 / 1000, getTime() % 1000 / 10
        )
    }
    private fun getTime() = SystemClock.elapsedRealtime() - base
    fun runTracker() {
        handler.post(runnable)
    }
}