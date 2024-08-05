package com.example.android_review06_kshn379

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review06_kshn379.databinding.CustomDialogBinding
import com.example.android_review06_kshn379.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val animalList: ArrayList<ZooInfo> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
    }

    override fun onResume() {
        super.onResume()
        // Fragment 화면 전환 시 마다 데이터 갱신
        getAdapterData()

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // toolbar 설정
            toolBarMain.apply {
                // menu 설정
                inflateMenu(R.menu.menu_filter)
            }
            // RecyclerView 설정
            recyclerViewMain.apply {
                // Adapter 연결
                adapter = ZooAdapter(animalList, parentFragmentManager)
                // LayoutManager 적용
                layoutManager = LinearLayoutManager(context)
                // RecyclerView Item 간격 추가
                addItemDecoration(VerticalSpaceItemDecoration(16))
                // FAB 설정 - 리사이클러뷰 하단 스크롤 시 FAB 서서히 사라지게 설정
                recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        // FAB 숨기거나 보이게 설정
                        if (dy > 0) {
                            // 아래로 스크롤 이동 했을 때 설정
                            binding.faButtonMain.animate()
                                .alpha(0f) // 투명
                                .setDuration(200) // 200 미리 초
                                .withEndAction { binding.faButtonMain.visibility = View.GONE }
                        } else {
                            // 위로 스크롤 이동 했을 때 또는 스크롤이 없는 경우 설정
                            binding.faButtonMain.animate()
                                .alpha(1f) // 불투명
                                .setDuration(200)
                                .withEndAction { binding.faButtonMain.visibility = View.VISIBLE }
                        }
                    }
                })
            }
        }
    }

    private fun getAdapterData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 백그라운드 Thread에서 데이터 가져오기
                val data = withContext(Dispatchers.IO) { AddDao.getAllData() }

                animalList.clear()
                animalList.addAll(data)
                // zooIdx로 정렬
                animalList.sortBy { it.zooIdx }
                // Adapter 변경 알림
                binding.recyclerViewMain.adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // Floating Action Button 설정
            faButtonMain.apply {
                setOnClickListener { fab ->
                    when (fab.id) {
                        R.id.faButton_main -> {
                            // AddFragment 화면 이동
                            moveFragment(FragmentName.ADD_FRAGMENT)
                        }
                    }
                }
            }
            // Toolbar 설정
            toolBarMain.apply {
                // menu 클릭 설정
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menufilter_item_select -> {
                            // 클릭 시 Dialog 화면 구현
                            // custom_dialog.xml 파일을 inflate 한다
                            val mDialogView: CustomDialogBinding =
                                DataBindingUtil.inflate(
                                    LayoutInflater.from(context),
                                    R.layout.custom_dialog,
                                    null,
                                    false
                                )
                            // AlertDialog 설정
                            val mBuilder = AlertDialog.Builder(context)
                                .setView(mDialogView.root)
                            // AlertDialog 객체 생성 및 화면 표시
                            val mAlertDialog = mBuilder.show()
                            // Dialog 외부 클릭 시 화면 닫히지 않게 설정
                            mAlertDialog.setCancelable(false)
                            mAlertDialog.setCanceledOnTouchOutside(false)

                            // 선택된 동물 종류를 저장할 mutableSetOf 설정
                            val selectAnimal = mutableSetOf("사자", "호랑이", "기린")

                            // 다이얼로그 필터 초기 전체 선택 설정
                            mDialogView.apply {
                                checkedTextViewAll.isChecked = true
                                checkedTextViewLion.isChecked = true
                                checkedTextViewTiger.isChecked = true
                                checkedTextViewGiraffe.isChecked = true

                                // 전체 선택 / 해제 설정
                                checkedTextViewAll.setOnClickListener {
                                    val isChecked = !checkedTextViewAll.isChecked
                                    checkedTextViewAll.isChecked = isChecked
                                    checkedTextViewLion.isChecked = isChecked
                                    checkedTextViewTiger.isChecked = isChecked
                                    checkedTextViewGiraffe.isChecked = isChecked
                                    // 선택된 동물 종류 업데이트
                                    if (isChecked) {
                                        selectAnimal.addAll(listOf("사자", "호랑이", "기린"))
                                    } else {
                                        // 전체 선택 해제 시 선택된 동물 종류 모두 제거
                                        selectAnimal.clear()
                                    }
                                }

                                // 동물 종류 선택 / 해제 시 설정

                                checkedTextViewLion.setOnClickListener {
                                    val isChecked = !checkedTextViewLion.isChecked
                                    checkedTextViewLion.isChecked = isChecked
                                    // 선택된 동물 상태 업데이트
                                    updateSelection("사자", isChecked, selectAnimal)
                                    // 전체 선택 상태 업데이트
                                    updateAllCheckState(mDialogView, selectAnimal)
                                }
                                checkedTextViewTiger.setOnClickListener {
                                    val isChecked = !checkedTextViewTiger.isChecked
                                    checkedTextViewTiger.isChecked = isChecked
                                    updateSelection("호랑이", isChecked, selectAnimal)
                                    updateAllCheckState(mDialogView, selectAnimal)
                                }
                                checkedTextViewGiraffe.setOnClickListener {
                                    val isChecked = !checkedTextViewGiraffe.isChecked
                                    checkedTextViewGiraffe.isChecked = isChecked
                                    updateSelection("기린", isChecked, selectAnimal)
                                    updateAllCheckState(mDialogView, selectAnimal)
                                }
                            }

                            // 확인 버튼 클릭 시 데이터 전달 후 다이얼로그 닫기
                            mDialogView.buttonSelectConfirm.setOnClickListener {
                                // 선택된 동물 종류 RecyclerView 업데이트
                                updateRecyclerView(selectAnimal)
                                mAlertDialog.dismiss()
                            }

                            // 취소 버튼 클릭 시 다이얼로그 닫기
                            mDialogView.buttonSelectCancel.setOnClickListener {
                                mAlertDialog.dismiss()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // RecyclerView Item 필터링 후 업데이트
    fun updateRecyclerView(selectedTypes: Set<String>) {
        // 선택된 동물 종류에 맞게 리스트 필터링
        val filteredList = animalList.filter { it.animalType in selectedTypes }
        // 필터링된 데이터 Adapter에 업데이트 하여 RecyclerView 업데이트
        (binding.recyclerViewMain.adapter as? ZooAdapter)?.updateData(filteredList)
    }


    // 선택된 동물 종류 업데이트
    fun updateSelection(animalType: String, isCheck: Boolean, selectAnimal: MutableSet<String>) {
        if (isCheck) {
            // 선택 활성화 시 선택된 동물 종류 추가
            selectAnimal.add(animalType)
        } else {
            // 선택 비활성화 시 선택된 동물 종류 제거
            selectAnimal.remove(animalType)
        }
    }

    // 전체 선택 상태 업데이트
    fun updateAllCheckState(dialogView: CustomDialogBinding, selectedTypes: Set<String>) {
        // 선택된 동물 종류가 전체 선택된 경우 전체 선택 활성화
        dialogView.checkedTextViewAll.isChecked = selectedTypes.size == 3
    }


    // 화면 이동
    private fun moveFragment(name: FragmentName) {
        when (name) {
            // AddFragment 로 이동
            FragmentName.ADD_FRAGMENT -> {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.containerMain, AddFragment())
                    .addToBackStack(name.str)
                    .commit()
            }

            else -> Log.d("wrong page", "Page 404 Error")
        }
    }
}


// RecyclerView Item 간격 추가
class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
    }
}