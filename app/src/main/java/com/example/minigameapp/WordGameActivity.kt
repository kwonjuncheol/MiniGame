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

class WordGameActivity : AppCompatActivity() {

    private val wordList = listOf(
        "APPLE", "WATER", "ALARM", "TRAIN", "SMILE",
        "GHOST", "MUSIC", "PLANT", "NIGHT", "RIVER"
    )

    private lateinit var targetWord: String
    private var tryCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_game)

        targetWord = wordList.random()

        val btnBack = findViewById<Button>(R.id.btn_back)
        val etInput = findViewById<EditText>(R.id.et_word_input)
        val btnGuess = findViewById<Button>(R.id.btn_guess)
        val tvHistory = findViewById<TextView>(R.id.tv_word_history)

        btnBack.setOnClickListener {
            finish() // 이 화면을 종료하고 이전(메인) 화면으로 돌아감
        }

        btnGuess.setOnClickListener {
            // 사용자가 입력한 글자를 가져와서 무조건 대문자로 변환
            val inputWord = etInput.text.toString().uppercase()

            // 5글자가 아니면 경고창 띄우고 중단
            if (inputWord.length != 5) {
                Toast.makeText(this, "5글자를 꽉 채워주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tryCount++
            var emojiResult = ""

            // 6. 5번 반복하면서 글자 하나하나를 비교하는 핵심 로직
            for (i in 0 until 5) {
                val inputChar = inputWord[i]
                val targetChar = targetWord[i]

                if (inputChar == targetChar) {
                    // 위치와 알파벳이 모두 똑같을 때 (초록)
                    emojiResult += "🟩"
                } else if (targetWord.contains(inputChar)) {
                    // 위치는 다르지만, 정답 단어 어딘가에 이 알파벳이 있을 때 (노랑)
                    emojiResult += "🟨"
                } else {
                    // 아예 없는 알파벳일 때 (회색/흰색)
                    emojiResult += "⬜"
                }
            }

            // 7. 결과 텍스트 만들기
            val currentHistory = tvHistory.text.toString()
            var newRecord = "[$tryCount] $inputWord\n     $emojiResult\n\n"

            // 정답을 맞췄을 경우의 처리
            if (inputWord == targetWord) {
                newRecord = "🎉 정답입니다! 🎉\n[$tryCount] $inputWord\n     $emojiResult\n\n"
                btnGuess.isEnabled = false // 게임이 끝났으니 버튼 비활성화
            }

            tvHistory.text = newRecord + currentHistory
            etInput.text.clear()
        }
    }
}