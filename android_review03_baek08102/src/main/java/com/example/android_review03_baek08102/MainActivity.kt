package com.example.android_review03_baek08102

import android.os.Bundle
import android.util.Log
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

    // 프래그먼트 전환 함수
    fun switchFragment(name: FragmentName) {
        when (name) {
            FragmentName.MAIN_FRAGMENT -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MainFragment())
                    .addToBackStack(FragmentName.MAIN_FRAGMENT.name)
                    .commit()
            }

            FragmentName.INPUT_FRAGMENT -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, InputFragment())
                    .addToBackStack(FragmentName.INPUT_FRAGMENT.name)
                    .commit()
            }

            FragmentName.SCORE_FRAGMENT -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, ScoreFragment())
                    .addToBackStack(FragmentName.SCORE_FRAGMENT.name)
                    .commit()
            }

            FragmentName.INFORM_FRAGMENT -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, InformFragment())
                    .addToBackStack(FragmentName.INFORM_FRAGMENT.name)
                    .commit()
            }
        }
    }
}