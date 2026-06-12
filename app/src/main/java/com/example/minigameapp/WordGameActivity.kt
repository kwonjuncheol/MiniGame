package com.example.minigameapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WordGameActivity : AppCompatActivity() {

    // 정답이 될 5자리 영단어 리스트 wordList. 랜덤으로 하나 뽑아서 targetWord로 설정함.
    private val wordList = listOf(
        "ALARM", "APPLE", "BEACH", "BRAVE", "BREAD",
        "CHAIR", "CLEAN", "CLOCK", "CLOUD", "DREAM",
        "EARTH", "GHOST", "GRAPE", "HAPPY", "HEART",
        "HOUSE", "JUICE", "LIGHT", "MAGIC", "MOUSE",
        "MUSIC", "NIGHT", "PLANT", "RIVER", "SMART",
        "SMILE", "SOUND", "SPACE", "STONE", "STORM",
        "SWEET", "TABLE", "TIGER", "TRAIN", "WATER"
    )
    private val targetWord = wordList.random()
    private var tryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_game)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val etInput = findViewById<EditText>(R.id.et_word_input)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvHistory = findViewById<TextView>(R.id.tv_word_history)

        btnBack.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {

            // 입력된 단어는 무조건 대문자로 변환함.
            val inputWord = etInput.text.toString().uppercase()

            // 5글자가 아니면 토스트메시지 띄우고 중단함.
            if (inputWord.length != 5) {
                Toast.makeText(this, "5글자 영단어로 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tryCount++
            var answerSquare = ""

            for (i in 0..4) {   // 1~5번째 자리 전부다 비교함.
                val inputChar = inputWord[i]
                val targetChar = targetWord[i]

                if (inputChar == targetChar) {
                    answerSquare += "✅"  // 위치와 알파벳이 정답.
                } else if (targetWord.contains(inputChar)) {
                    answerSquare += "🟨"  // 위치는 다른데 글자가 있긴 함.
                } else {
                    answerSquare += "⬛"  // 아예 없는 알파벳임.
                }
            }

            var newRecord = "[$tryCount] $inputWord\n$answerSquare\n"

            if (inputWord == targetWord) {
                newRecord = "정답!!\n[$tryCount] $inputWord\n$answerSquare\n"
                btnSubmit.isEnabled = false // 게임이 끝났으니 버튼 비활성화
            }
            tvHistory.append(newRecord)
            etInput.text.clear()
        }
    }
}