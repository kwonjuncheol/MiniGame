package com.example.minigameapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class TimingGameActivity : AppCompatActivity() {

    var isRunning = false
    var startTime = 0L
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timing_game)

        val tvTimer = findViewById<TextView>(R.id.tv_timer)
        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnAction = findViewById<Button>(R.id.btn_start)
        val tvResult = findViewById<TextView>(R.id.tv_result)

        val runnable = object : Runnable {
            override fun run() {
                val passedTime = System.currentTimeMillis() - startTime
                val seconds = passedTime / 1000.0

                tvTimer.text = String.format(Locale.getDefault(), "%.2f", seconds)
                handler.postDelayed(this, 10)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnAction.setOnClickListener {
            if (!isRunning) {
                tvResult.text = ""
                btnAction.text = "STOP!"
                startTime = System.currentTimeMillis()
                isRunning = true
                handler.post(runnable)

            } else {
                handler.removeCallbacks(runnable)
                isRunning = false
                btnAction.text = "다시 시작"

                val finalTimeStr = tvTimer.text.toString()
                val finalTime = finalTimeStr.toDouble()
                val diff = Math.abs(5.0 - finalTime)

                val resultMessage = when {
                    diff == 0.0 -> "성공!!!"
                    else -> "오차: ${String.format("%.2f", diff)}초"
                }

                tvResult.text = resultMessage
            }
        }
    }
}