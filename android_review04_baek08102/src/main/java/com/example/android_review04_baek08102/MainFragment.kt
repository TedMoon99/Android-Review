package com.example.android_review04_baek08102

import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review04_baek08102.databinding.ActivityMainBinding
import com.example.android_review04_baek08102.databinding.FragmentMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<StudentData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingData()
        settingView()
        settingEvent()
    }

    fun settingView() {
        binding.apply {

            // recyclerView 관련 작업
            val context = requireContext()
            // 가로 구분선
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }
            mainRecyclerView.apply {
                // settingData()로 받아온 데이터 dataList에 담은 뒤 어댑터에 연결
                adapter = CustomAdapter(dataList, parentFragmentManager)

                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco) // 가로 구분선 리사이클러뷰에 추가
            }

            // toolbar 관련 작업
            mainToolBar.apply {
                setTitle("당신의 점수는?")
                setNavigationIcon(R.drawable.photo_camera_24px)
                inflateMenu(R.menu.menu_main)
            }
        }

    }

    fun settingEvent() {
        binding.apply {
            // toolbar 관련 event 설정
            mainToolBar.apply {
                // navigation icon 클릭시
                setNavigationOnClickListener {
                    val toast = Toast.makeText(context, "찰칵!", Toast.LENGTH_SHORT)
                    toast.show()
                }
                // 메뉴 아이템 클릭 이벤트
                setOnMenuItemClickListener { item ->
                    when (item.itemId) { // itemId에 따라 분기
                        R.id.menu_main_input -> { // 학생 정보 입력 클릭 시
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, SubFragment1()) // SubFragment1로 화면 전환
                                .addToBackStack(FragmentName.SUB_FRAGMENT1.name)
                                .commit()
                        }

                        R.id.menu_main_total -> {

                            if (dataList.size != 0) { // 학생 정보 입력 받았을 시에만 실행
                                val data = dataList // 받아온 데이터 옮긴 후
                                val bundle = Bundle().apply {
                                    putParcelableArrayList("studentTotal", data) // 번들에 담아준 뒤
                                }
                                val subFragment2 = SubFragment2()
                                subFragment2.arguments = bundle // SubFragment2의 argument로 번들을 넣어주고

                                parentFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.main_container, subFragment2) // SubFragment2로 화면 전환
                                    .addToBackStack(FragmentName.SUB_FRAGMENT2.name)
                                    .commit()
                            } else { // 정보 입력 아직 받지 않았다면 토스트 출력
                                Toast.makeText(context, "정보 입력을 먼저 해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // 정보 입력 화면에서 전달받은 데이터 세팅하는 함수
    fun settingData() {

        // SetFragmentResult로 전달해준 데이터 ~Listener로 받음
        setFragmentResultListener("Done Input") { requestKey, bundle ->
            // API 레벨 호환성 고려
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("inputData", StudentData::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable("inputData")
            }

            if (result != null) { // 전달값이 제대로 전달되었다면
                dataList.add(result) // dataList에 담은 뒤
                // 어댑터에게 데이터 변경만 알림 (담는 건 SettingView()에서)
                binding.mainRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

}

