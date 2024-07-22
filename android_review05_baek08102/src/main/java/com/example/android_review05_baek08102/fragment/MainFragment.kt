package com.example.android_review05_baek08102.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.adapter.CustomAdapter
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.databinding.FragmentMainBinding
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.viewmodel.ShowViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<StudentData> = arrayListOf()
    private val showViewModel: ShowViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Fragment가 화면에 다시 나타날 때마다 데이터 갱신
        settingData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingView()
        settingEvent()
        updateItemList()
    }

    // 뷰 설정 함수
    fun settingView() {
        binding.apply {
            // 툴바 설정
            mainToolbar.apply {
                setTitle("학생 정보 관리")
                inflateMenu(R.menu.menu_main)
            }
            val context = requireContext()
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }
            mainRecyclerView.apply {

                val menuInflater = requireActivity().menuInflater // menuinflater 객체 생성 후

                // adapter에게 인자로 넘겨줌
                adapter = CustomAdapter(dataList, parentFragmentManager, menuInflater, showViewModel)
                adapter?.notifyDataSetChanged() // adapter 연결하였으니 데이터 갱신 요구

                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)
            }
        }
    }

    // 이벤트 설정 함수
    fun settingEvent() {
        binding.apply {
            mainToolbar.apply {
                // 툴바 아이템 클릭 시
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_add -> {
                            // 화면 전환
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_containerView, InputFragment())
                                .addToBackStack(FragmentName.Input_Fragment.name)
                                .commit()
                        }
                    }
                    true
                }
            }
        }
    }

    // 데이터 설정 함수
    fun settingData() {
        // 비동기 작업 시 lifecycleScope 안에서만 처리되도록
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {// 데이터를 로드해오는 작업은 Dispatchers.IO통해
                    StudentDao.getAllData() // 백그라운드 스레드에서 실행되도록
                }
                // 아래는 메인 스래드에서 실행
                dataList.clear() // dataList 비우고
                dataList.addAll(data) // getAllData()로 받아온 데이터 dataList에 저장
                dataList.sortBy { it.studentIdx } // studentIdx 필드의 값 기준으로 정렬하고
                binding.mainRecyclerView.adapter?.notifyDataSetChanged() // 데이터 변경 어댑터에게 알림

            } catch (e: Exception) {
                Log.e("MainFragment", "settingData failed : ${e.message}")
            }
        }
    }

    // 리사이클러뷰 아이템 리스트 갱신 함수
    fun updateItemList() {
        // 사용자가 recyclerView Item의 컨텍스트 메뉴 클릭 시 갱신되는 clickedPosition 감시
        showViewModel.clickedPosition.observe(viewLifecycleOwner) { position -> // 해당 데이터 변경 시
            if (position != null && position >= 0 && position < dataList.size) {

                Log.d("test11", "updateData has called -> observed position : ${position}")

                dataList.removeAt(position) // position번째 데이터 dataList에서 삭제 후
                binding.mainRecyclerView.adapter?.notifyItemRemoved(position)
                // adapter에게 해당 번째 아이템 삭제되었다 알리고
                binding.mainRecyclerView.adapter?.notifyItemRangeRemoved(position, dataList.size - position)
                // 해당 아이템의 position 이후의 item들의 position 재정렬
            }
        }
    }
}