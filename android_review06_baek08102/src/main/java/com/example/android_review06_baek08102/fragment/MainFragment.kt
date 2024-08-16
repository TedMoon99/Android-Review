package com.example.android_review06_baek08102.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_review06_baek08102.R
import com.example.android_review06_baek08102.adapter.CustomAdapter
import com.example.android_review06_baek08102.databinding.DialogMainBinding
import com.example.android_review06_baek08102.databinding.FragmentMainBinding
import com.example.android_review06_baek08102.model.UnifiedAnimalData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.android_review06_baek08102.utils.FragmentName
import com.example.android_review06_baek08102.viewmodel.ShowViewModel
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val dataList: ArrayList<UnifiedAnimalData> = arrayListOf()
    private val viewModel: ShowViewModel by activityViewModels()

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

        settingData()
        settingView()
        settingEvent()
    }

    // 뷰 설정
    fun settingView() {
        binding.apply {
            mainToolbar.apply {
                setTitle("동물원 관리")
                inflateMenu(R.menu.menu_main)
            }

            val context = requireContext()
            val deco = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                isLastItemDecorated = false
            }
            mainRecyclerView.apply {

                layoutManager = LinearLayoutManager(context)
                addItemDecoration(deco)

                adapter = CustomAdapter(dataList, parentFragmentManager, viewModel)
            }

            onSaveSuccess()
        }

    }

    // 이벤트 설정
    fun settingEvent() {
        binding.apply {
            mainToolbar.apply {
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_filter -> {
                            showDialog()
                        }
                    }
                    true
                }
            }
            mainFabMain.setOnClickListener {
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, InputFragment())
                    .addToBackStack(FragmentName.Input_Fragment.name)
                    .commit()
            }
        }
        // 메뉴 클릭 이벤트

    }

    // 다이얼로그 출력
    fun showDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val dialogBinding = DialogMainBinding.inflate(layoutInflater)

        builder.apply {

            setView(dialogBinding.root)
            settingCheckedState(dialogBinding)

            setPositiveButton("확인") { dialog, id ->
                // 확인 버튼 클릭 시
                // 체크박스 조건에 따라 리사이클러뷰 갱신 필요
                // 해당 작업 함수 만든 뒤 호출하면 될 듯

                settingViewByChecked(dialogBinding)
            }
            setNegativeButton("취소") { dialog, id ->
                // 취소 버튼 클릭 시 다이얼로그만 종료되면 됨
            }
            builder.show()
        }
    }

    // 다이얼로그 체크박스 체크 조건으로 출력 조정
    fun settingCheckedState(binding: DialogMainBinding) {

        binding.apply {

            // 체크박스 조건 설정
            // All 체크박스의 체크 조건
            val updateAllCheckBoxState: () -> Unit = {
                // lion, tiger, giraffe 가 모두 체크된다면
                val allChecked = dialogCheckBoxLion.isChecked &&
                        dialogCheckBoxTiger.isChecked &&
                        dialogCheckBoxGiraffe.isChecked

                // all 박스 체크, 그렇지 않다면 체크 해제
                dialogCheckBoxAll.isChecked = allChecked
            }

            // 체크박스 리스너
            // All 체크 시 모두 체크
            dialogCheckBoxAll.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dialogCheckBoxLion.isChecked = true
                    dialogCheckBoxTiger.isChecked = true
                    dialogCheckBoxGiraffe.isChecked = true
                }
            }

            // lion, tiger, giraffe 체크 혹은 해제 시 all 체크 조건 검사
            dialogCheckBoxLion.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
            dialogCheckBoxTiger.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
            dialogCheckBoxGiraffe.setOnCheckedChangeListener { _, _ -> updateAllCheckBoxState() }
        }
    }

    // 어댑터로 전달해 줄 데이터 세팅 함수
    fun settingData() {

        Log.d("addSnapshotListener process","mainFragment - settingData called")

        binding.apply {
            // Dao에서 addSnapshotListener를 통한 실시간 감시 가능하도록 만듬,
            // viewModel 내의 LiveData 타입 mainDataList 감시하여
            // 해당 데이터 변경 시 자동으로 dataList 갱신
            viewModel.mainDataList.observe(viewLifecycleOwner) { viewModelData ->

                Log.d("addSnapshotListener process","settingData -> before process dataList : $dataList")

                val data = viewModel.mainDataList.value!!

                Log.d("addSnapshotListener process","settingData -> LiveDataList : $data")

                dataList.clear()
                dataList.addAll(data)
                dataList.sortBy { it.animalIdx }

                Log.d("addSnapshotListener process","settingData -> after process dataList : $dataList")

                // dataList 갱신 후 adapter에게 연결된 데이터의 변경 알림
                mainRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    // 데이터 입력 성공 여부에 따라 동작하는 함수
    fun onSaveSuccess() {
        // InputFragment로부터 데이터 전달 성공 여부 전달받음
        setFragmentResultListener("Success") { requestKey, bundle ->
            val success = bundle.getBoolean("success")

            if (success) {
                showToast("데이터 저장 성공 !!")
            } else {
                showToast("데이터 저장 실패 ㅠㅠ")
            }
        }

    }

    // 출력하고자 하는 message 인자로 받아 Toast 띄우는 ㅎ마수
    fun showToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun settingViewByChecked(binding: DialogMainBinding) {

    }
}