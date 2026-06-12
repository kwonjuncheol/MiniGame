package com.example.minigameapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BlackJackActivity : AppCompatActivity() {

    // 실제 카드 팩처럼 1(A)~11, JKQ는 10취급함.
    private val cardDeck = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11)
    private var myScore = 0
    private var myCardsText = ""
    private var isGameOver = false
    private var aceCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_jack)

        val tvDealerScore = findViewById<TextView>(R.id.tv_dealer_score)
        val tvMyScore = findViewById<TextView>(R.id.tv_my_score)
        val tvMyCards = findViewById<TextView>(R.id.tv_my_cards)
        val tvResult = findViewById<TextView>(R.id.tv_blackjack_result)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnDraw = findViewById<Button>(R.id.btn_draw)
        val btnStop = findViewById<Button>(R.id.btn_stop)
        val btnRestart = findViewById<Button>(R.id.btn_restart)

        btnBack.setOnClickListener {
            finish()
        }

        btnDraw.setOnClickListener {
            if (isGameOver) return@setOnClickListener // 게임 끝났으면 클릭 무시

            val newCard = cardDeck.random()

            //  뽑은 카드가 에이스면 카운트 1증가후 [A]로 표시
            if (newCard == 11) {
                aceCount++
                myCardsText += "[A] "
            } else {
                myCardsText += "[$newCard] "
            }

            myScore += newCard

            // 21초과시 A가 있다면 10깎아서 1로 계산될 여지 줌
            while (myScore > 21 && aceCount > 0) {
                myScore -= 10
                aceCount--
            }

            // 화면갱신
            tvMyScore.text = "내 점수: $myScore"
            tvMyCards.text = "뽑은 카드: $myCardsText"

            // 승패 검사
            if (myScore > 21) {
                tvResult.text = "21 초과. 패배입니다."
                resetGame(btnDraw, btnStop, btnRestart)
            } else if (myScore == 21) {
                tvResult.text = "블랙잭! 승리입니다."
                resetGame(btnDraw, btnStop, btnRestart)
            }
        }

        // 스톱을 누르면 딜러와 점수비교하여 승패결정, 딜러점수는 16에서 21임.
        btnStop.setOnClickListener {
            if (isGameOver || myScore == 0) return@setOnClickListener
            val dealerScore = (16..21).random()

            tvDealerScore.text = "딜러 점수: $dealerScore"

            when {
                myScore > dealerScore -> tvResult.text = "승리!"
                myScore < dealerScore -> tvResult.text = "패배!"
                else -> tvResult.text = "무승부!"
            }

            resetGame(btnDraw, btnStop, btnRestart)
        }

        // 다시하기 누르면 초기화함. 점수, 에이스, 승패 등..
        btnRestart.setOnClickListener {
            myScore = 0
            aceCount = 0
            myCardsText = ""
            isGameOver = false

            tvDealerScore.text = "딜러 점수: 스톱하면 공개됨"
            tvMyScore.text = "현재 점수: 0"
            tvMyCards.text = "뽑은 카드: "
            tvResult.text = "카드를 뽑거나 스톱을 눌러주세요"

            // 버튼 상태 되돌리고 다시하기 버튼을 숨김
            btnDraw.isEnabled = true
            btnStop.isEnabled = true
            btnRestart.visibility = View.INVISIBLE
        }
    }

    // 게임 종료 시 버튼들을 비활성화하고 다시하기 버튼을 띄우는 함수
    private fun resetGame(btnDraw: Button, btnStop: Button, btnRestart: Button) {
        isGameOver = true
        btnDraw.isEnabled = false
        btnStop.isEnabled = false
        btnRestart.visibility = View.VISIBLE
    }
}