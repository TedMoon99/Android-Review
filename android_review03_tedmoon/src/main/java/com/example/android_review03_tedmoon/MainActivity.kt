package com.example.android_review03_tedmoon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_review03_tedmoon.databinding.ActivityMainBinding
import com.example.android_review03_tedmoon.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 화면 이동
        startDevice()
    }

    // 화면 이동
    private fun startDevice(){
        binding.apply {
            // MainFragment 출력 설정
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.containerMain, MainFragment())
                    .commit() // 트랜잭션 생성
            }
        }
    }
}