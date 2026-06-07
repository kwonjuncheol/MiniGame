package com.example.minigameapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BlackJackActivity : AppCompatActivity() {

    private val cardDeck = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11)

    private var myScore = 0
    private var myCardsText = ""
    private var isGameOver = false

    // 에이스의 개수 저장
    private var aceCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_jack)

        val tvDealerScore = findViewById<TextView>(R.id.tv_dealer_score)
        val tvMyScore = findViewById<TextView>(R.id.tv_my_score)
        val tvMyCards = findViewById<TextView>(R.id.tv_my_cards)
        val tvResult = findViewById<TextView>(R.id.tv_bj_result)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnHit = findViewById<Button>(R.id.btn_hit)
        val btnStand = findViewById<Button>(R.id.btn_stand)
        val btnRestart = findViewById<Button>(R.id.btn_restart)

        btnHit.setOnClickListener {
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
                endGame(btnHit, btnStand, btnRestart)
            } else if (myScore == 21) {
                tvResult.text = "블랙잭! 승리입니다."
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        // 3. [Stand] 버튼: 내 차례를 멈추고 딜러와 점수 비교하기
        btnStand.setOnClickListener {
            if (isGameOver || myScore == 0) return@setOnClickListener

            // 딜러의 점수 생성 (15 ~ 21 사이의 랜덤 값)
            val dealerScore = (15..21).random()
            tvDealerScore.text = "딜러 점수: $dealerScore"

            // 승패 판정
            when {
                myScore > dealerScore -> tvResult.text = "승리!"
                myScore < dealerScore -> tvResult.text = "패배!"
                else -> tvResult.text = "무승부!"
            }

            endGame(btnHit, btnStand, btnRestart)
        }

        // 4. [다시 하기] 버튼: 모든 상태를 초기화
        btnRestart.setOnClickListener {
            myScore = 0
            aceCount = 0 // 에이스 개수도 0으로 초기화
            myCardsText = ""
            isGameOver = false

            tvDealerScore.text = "딜러 점수: ???"
            tvMyScore.text = "현재 점수: 0"
            tvMyCards.text = "뽑은 카드: "
            tvResult.text = "카드를 뽑아주세요!"

            // 버튼 상태 되돌리기
            btnHit.isEnabled = true
            btnStand.isEnabled = true
            btnRestart.visibility = View.GONE // 다시하기 버튼 숨기기
        }
    }

    // 게임 종료 시 버튼들을 비활성화하고 '다시 하기' 버튼을 띄우는 함수
    private fun endGame(btnHit: Button, btnStand: Button, btnRestart: Button) {
        isGameOver = true
        btnHit.isEnabled = false
        btnStand.isEnabled = false
        btnRestart.visibility = View.VISIBLE // 숨겨뒀던 다시하기 버튼 보여주기
    }
}