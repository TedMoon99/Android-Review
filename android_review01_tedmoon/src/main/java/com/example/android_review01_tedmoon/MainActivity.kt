package com.example.android_review01_tedmoon

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
    }

    // 화면 이동
    fun startDevice(){
        supportFragmentManager
            .beginTransaction() // 트랜잭션 생성
            .replace(R.id.containerMain, MainFragment()) // FragmentContainerView -> MainFragment 화면 출력
//            .addToBackStack(false) 화면전환 시에는 BackStack에 추가해야 되는 것이 맞지만 가장 기본이 되는 Fragment는 추가하지 않는다
            .commit() // 실행



    }

}