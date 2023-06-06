package com.example.agroconnect

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer

class SessionManager(private val context: Context) {

    companion object {
        private const val SESSION_PREFERENCES = "session_preferences"
        private const val KEY_SESSION_START_TIME = "session_start_time"
        private const val SESSION_DURATION = 1 * 60 * 1000L // 20 minutes in milliseconds
    }

    private val sessionPreferences: SharedPreferences =
        context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)

    private var sessionTimer: CountDownTimer? = null

    fun startSession() {
        val sessionStartTime = System.currentTimeMillis()
        sessionPreferences.edit().putLong(KEY_SESSION_START_TIME, sessionStartTime).apply()

        startTimer()
    }

    fun resetSession() {
        cancelTimer()
        startTimer()
    }

    fun endSession() {
        cancelTimer()
        sessionPreferences.edit().clear().apply()
    }

    private fun startTimer() {
        sessionTimer?.cancel()

        sessionTimer = object : CountDownTimer(SESSION_DURATION, SESSION_DURATION) {
            override fun onTick(millisUntilFinished: Long) {
                // Not used in this implementation
            }

            override fun onFinish() {
                endSession()
                // Perform any additional actions when the session expires
            }
        }.start()
    }

    private fun cancelTimer() {
        sessionTimer?.cancel()
        sessionTimer = null
    }

    fun isSessionActive(): Boolean {
        val sessionStartTime = sessionPreferences.getLong(KEY_SESSION_START_TIME, 0)
        val elapsedTime = System.currentTimeMillis() - sessionStartTime
        return elapsedTime < SESSION_DURATION
    }
}
