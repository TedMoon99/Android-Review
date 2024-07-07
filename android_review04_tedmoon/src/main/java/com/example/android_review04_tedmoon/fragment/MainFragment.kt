package com.example.android_review04_tedmoon.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review04_tedmoon.R
import com.example.android_review04_tedmoon.adapter.CustomAdapter
import com.example.android_review04_tedmoon.dao.ScoreDao
import com.example.android_review04_tedmoon.databinding.FragmentMainBinding
import com.example.android_review04_tedmoon.model.ScoreInfo
import com.example.android_review04_tedmoon.utils.FragmentName
import com.example.android_review04_tedmoon.viewmodel.MainViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    var dataList: ArrayList<ScoreInfo> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // Toolbar
            toolbarMain.apply {
                // menu
                inflateMenu(R.menu.menu_main)
            }
            // RecyclerView
            recyclerViewMain.apply {
                // 구분선 생성
                val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
//                // 데이터 불러오기
                gettingAdapterData()

                // 어댑터 연결
                adapter = CustomAdapter(dataList)
                // 레이아웃 매니저 적용
                layoutManager = LinearLayoutManager(this@MainFragment.context)
                // 구분선 적용
                addItemDecoration(deco)
            }
        }
    }

    // DB에서 모든 정보를 가져온다
    fun gettingAdapterData() {
        CoroutineScope(Dispatchers.Main).launch {
            // DB에서 모든 정보를 가져온다
            val data = ScoreDao.getAllData()
            Log.d("test1234", "DB에서 불러온 데이터 : ${data}")
            if (data.isNotEmpty()) {
                dataList = data
            } else {
                Log.d("test1234", "DB에서 불러온 데이터가 비어있습니다")
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // Toolbar 리스너
            toolbarMain.apply {
                // menu Item 리스너 설정
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 학생 정보 입력 클릭 시
                        R.id.menuItem_main_add -> {
                            // 화면 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                        // 총점 및 평균 클릭 시
                        R.id.menuItem_main_totalAverage -> {
                            // 화면 이동
                            moveFragment(FragmentName.INFO_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }


    // 화면 이동 함수
    private fun moveFragment(name: FragmentName) {
        when (name) {
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction() // 트랜잭션 생성
                    .replace(R.id.containerMain, AddFragment()) // 화면 전환
                    .addToBackStack(name.str) // 백스택에 이름 추가
                    .commit() // 실행
            }

            FragmentName.INFO_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction() // 트랜잭션 생성
                    .replace(R.id.containerMain, InfoFragment()) // 화면전환
                    .addToBackStack(name.str) // 백스택에 이름 추가
                    .commit() // 실행
            }

            else -> Log.d("test1234", "잘못된 이동입니다")
        }
    }
}