package com.example.android_review01_kshn379

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var inputId: EditText
    private lateinit var inputName: EditText
    private lateinit var checkProfileButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    // 기존 프로필 목록
    private val profileList = arrayListOf(
        Profiles(R.drawable.profile, "KSH","Android0", "*asnjgh12!"),
        Profiles(R.drawable.profile, "MTJ","Android1", "1235a945!"),
        Profiles(R.drawable.profile, "BDS","Android2", "321657!*"),
        Profiles(R.drawable.profile, "HSH","Android3", "gnjf53a1!*"),
        Profiles(R.drawable.profile, "KNY","Android4", "rnjfd0109**"),
        Profiles(R.drawable.profile, "KMS","Android5", "hk526ctb*!"),
        Profiles(R.drawable.profile, "JMG","Android6", "g5f3z5n6!"),
        Profiles(R.drawable.profile, "CSW","Android7", "gnfjh395!*"),
        Profiles(R.drawable.profile, "KJM","Android8", "hnjkf0912*"),
        Profiles(R.drawable.profile, "YNA","Android9", "6815hf2t*")
    )

    // 출력할 프로필 목록
    private val displayedProfileList = arrayListOf<Profiles>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputId = findViewById(R.id.input_id)
        inputName = findViewById(R.id.input_name)
        checkProfileButton = findViewById(R.id.checkprofile)
        recyclerView = findViewById(R.id.rv_profile)

        profileAdapter = ProfileAdapter(displayedProfileList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = profileAdapter

        checkProfileButton.setOnClickListener {
            val id = inputId.text.toString().trim()
            val name = inputName.text.toString().trim()

            if (id.isNotEmpty() && name.isNotEmpty()) {
                val matchingProfile = profileList.find { profile ->
                    profile.id == id && profile.name == name
                }

                if (matchingProfile != null) {
                    // 리사이클러 뷰 리스트 삭제
//                    displayedProfileList.clear()
                    displayedProfileList.add(matchingProfile)
                    profileAdapter.notifyDataSetChanged()

                    // 입력 후 EditText 초기화
                    inputId.text.clear()
                    inputName.text.clear()
                } else {
                    // 일치하는 항목이 없을 때 처리
                    inputId.error = "일치하는 아이디가 없습니다."
                    inputName.error = "일치하는 이름이 없습니다."
                }
            }
        }
    }
}


