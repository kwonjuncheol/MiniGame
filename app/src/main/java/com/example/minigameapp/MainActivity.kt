package com.example.minigameapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnUpDown = findViewById<Button>(R.id.btn_up_down)
        val btnTiming = findViewById<Button>(R.id.btn_timing)
        val btnWord = findViewById<Button>(R.id.btn_word)
        val btnBlackjack = findViewById<Button>(R.id.btn_blackjack)

        btnUpDown.setOnClickListener {
            val intent = Intent(this, UpDownActivity::class.java)
            startActivity(intent)
        }

        btnTiming.setOnClickListener {
            val intent = Intent(this, TimingGameActivity::class.java)
            startActivity(intent)
        }

        btnWord.setOnClickListener {
            val intent = Intent(this, WordGameActivity::class.java)
            startActivity(intent)
        }

        btnBlackjack.setOnClickListener {
            val intent = Intent(this, BlackJackActivity::class.java)
            startActivity(intent)
        }

    }
}