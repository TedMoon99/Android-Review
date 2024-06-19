package com.example.android_review02_tedmoon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 화면이동
        startDevice()
    }

    // 화면이동
    fun startDevice(){
        supportFragmentManager
            .beginTransaction() // 트랜잭션을 생성한다
            .replace(R.id.containerMain, MainFragment()) // FragmentContainer -> MainFragment() 화면 출력
            .commit() // 실행한다
    }
}