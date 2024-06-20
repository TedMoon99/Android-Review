package com.example.android_review02_baek08102

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_review02_baek08102.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startDevice()
    }

    // 화면 시작 위한 함수 세팅
    fun startDevice() {
        supportFragmentManager // 프래그먼트 추가, 교체, 제거하는 작업 수행
            .beginTransaction() // 새로운 트랜잭션 생성
            .replace(R.id.main_container, MainFragment()) // FragmentContainerView를 MainFragment로 교체
            .commit() // 트랜잭션 실행
    }
}