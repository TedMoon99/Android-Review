package com.example.android_review04_tedmoon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.android_review04_tedmoon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // DataBinding 적용 시 Activity에서의 Binding 코드
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // binding lifecycleOwner 적용 -> View가 Fragment보다 먼저 죽을 수 있기에
        // 메모리 누수(memory Leak)가 발생할 수 있는데 lifecycleOwner를 설정해줌으로서 이를 방지할 수 있다
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}