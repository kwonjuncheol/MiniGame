package com.example.minigameapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class UpDownActivity : AppCompatActivity() {

    var targetNumber = 0
    var tryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_down)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val tvResult = findViewById<TextView>(R.id.tv_result)
        val etInput = findViewById<EditText>(R.id.et_input)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvHistory = findViewById<TextView>(R.id.tv_history)

        targetNumber = Random.nextInt(1, 101)

        btnBack.setOnClickListener {
            finish() // 이 화면을 종료하고 이전(메인) 화면으로 돌아감
        }

        btnSubmit.setOnClickListener {
            val inputStr = etInput.text.toString()

            // 공백입력시 예외처리, 코드 미실행 및 중단함
            if (inputStr.isEmpty()) {
                Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputNum = inputStr.toInt()
            tryCount++

            var historyResult = ""

            when {
                inputNum > targetNumber -> {
                    historyResult = "Down 👇"
                    tvResult.text = "입력한 숫자: $inputNum\n👇 Down! (시도: ${tryCount}번)"
                }
                inputNum < targetNumber -> {
                    historyResult = "Up 👆"
                    tvResult.text = "입력한 숫자: $inputNum\n👆 Up! (시도: ${tryCount}번)"
                }
                else -> {
                    historyResult = "정답 🎉"
                    tvResult.text = "정답입니다! ($inputNum)\n${tryCount}번 만에 맞췄습니다!"
                    btnSubmit.isEnabled = false
                }
            }

            val currentHistory = tvHistory.text.toString()
            val newRecord = "[${tryCount}번째] $inputNum ➔ $historyResult\n"
            tvHistory.text = newRecord + currentHistory

            etInput.text.clear()
        }
    }
}

