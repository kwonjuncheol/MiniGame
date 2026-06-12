package com.example.minigameapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        // 1부터 100까지의 자연수중에 하나를 정답으로 설정함
        targetNumber = Random.nextInt(1, 101)

        btnBack.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {
            val inputStr = etInput.text.toString()
            // 공백입력시 예외처리하여 실행하지않고 토스트메시지 띄움.
            if (inputStr.isEmpty()) {
                Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputNum = inputStr.toInt()
            tryCount++

            var historyResult = ""

            when {
                inputNum > targetNumber -> {
                    historyResult = "Down"
                    tvResult.text = "입력한 숫자: $inputNum\n Down! (시도: ${tryCount}번)"
                }

                inputNum < targetNumber -> {
                    historyResult = "Up"
                    tvResult.text = "입력한 숫자: $inputNum\n Up! (시도: ${tryCount}번)"
                }

                else -> {
                    historyResult = "정답"
                    tvResult.text = "정답! ($inputNum)\n${tryCount}번에 성공!"
                    btnSubmit.isEnabled = false
                }
            }

            // 기존 기록에 이어서 최근 입력값의 결과를 아래로 붙여서 추가해보여줌.
            val newRecord = "[${tryCount}번째] $inputNum ➔ $historyResult\n"
            tvHistory.append(newRecord)
            etInput.text.clear()
        }
    }
}

