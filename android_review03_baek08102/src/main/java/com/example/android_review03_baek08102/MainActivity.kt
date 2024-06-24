package com.example.android_review03_baek08102

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_review03_baek08102.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 화면 출력 
        startDevice()
    }

    // fragment_main 화면 출력 함수
    fun startDevice() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, MainFragment())
            .commit()
    }
}