package com.example.minigameapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class TimingGameActivity : AppCompatActivity() {

    var isRunning = false
    var startTime = 0L

    val handler = Handler(Looper.getMainLooper())
    lateinit var tvTimer: TextView // 타이머 텍스트뷰를 밖에서도 쓰기 위해 선언


    private val runnable = object : Runnable {
        override fun run() {
            // 현재 시간에서 시작 시간을 빼서 흘러간 시간 계산
            val elapsedTime = System.currentTimeMillis() - startTime
            val seconds = elapsedTime / 1000.0 // 밀리초를 초 단위로 변환

            // 화면에 0.00 형식으로 소수점 둘째 자리까지 표시
            tvTimer.text = String.format(Locale.getDefault(), "%.2f", seconds)

            // 10밀리초(0.01초) 뒤에 자기 자신을 다시 실행하도록 예약 (무한 반복)
            handler.postDelayed(this, 10)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timing_game)

        tvTimer = findViewById(R.id.tv_timer)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnAction = findViewById<Button>(R.id.btn_action)
        val tvResult = findViewById<TextView>(R.id.tv_result)

        btnBack.setOnClickListener {
            finish() // 이 화면을 종료하고 이전(메인) 화면으로 돌아감
        }
        btnAction.setOnClickListener {
            if (!isRunning) {
                // [시작] 상태일 때 누른 경우
                tvResult.text = "" // 결과 문구 지우기
                btnAction.text = "STOP!" // 버튼 글자 바꾸기
                startTime = System.currentTimeMillis() // 현재 시간 기록
                isRunning = true
                handler.post(runnable) // ⏱️ 스톱워치 굴러가기 시작!

            } else {
                // [정지(STOP)] 상태일 때 누른 경우
                handler.removeCallbacks(runnable) // ⏱️ 스톱워치 강제 멈춤!
                isRunning = false
                btnAction.text = "다시 시작"

                // 정확히 몇 초에 멈췄는지 최종 계산
                val finalTimeStr = tvTimer.text.toString()
                val finalTime = finalTimeStr.toDouble()

                // 목표 시간(5.00)과의 오차 계산 (절댓값)
                val diff = Math.abs(5.0 - finalTime)

                // 오차에 따른 등급 판정
                val resultMessage = when {
                    diff == 0.0 -> "성공!!!"
                    else -> "오차: ${String.format("%.2f", diff)}초"
                }

                tvResult.text = resultMessage
            }
        }

    }
}