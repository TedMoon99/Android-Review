package com.example.android_review06_kshn379

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.addViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
        // 입력 요소 초기화
        settingInput()
    }

    // 입력 요소 초기화
    private fun settingInput() {
        viewModel.clearText()
    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // TextField 숨기기
            editTextAnimalName.visibility = View.INVISIBLE
            editTextAnimalAge.visibility = View.INVISIBLE
            editTextAnimalCount.visibility = View.INVISIBLE
            editTextAnimalDetail.visibility = View.INVISIBLE

            // Toolbar 설정
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_items)
            }
        }
        // Error 설정
        settingError()
    }

    // Error 설정
    fun settingError() {
        binding.apply {
            // name
            viewModel.animalName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..7) {
                        editTextAnimalName.error = null
                    } else {
                        editTextAnimalName.error = "동물 이름은 6자 이하로 입력 하세요"
                    }
                } else {
                    editTextAnimalName.error = null
                }
            }
            // age
            viewModel.animalAge.observe(viewLifecycleOwner) { age ->
                if (age != null && age.isNotEmpty()) {
                    if (age.toInt() in 1..100) {
                        editTextAnimalAge.error = null
                    } else {
                        editTextAnimalAge.error = "동물 나이는 100살 이하로 입력해 주세요"
                    }
                } else {
                    editTextAnimalAge.error = null
                }
            }

            // count
            viewModel.animalCount.observe(viewLifecycleOwner) { count ->
                if (count != null && count.isNotEmpty()) {
                    if (count.toInt() in 1..100) {
                        editTextAnimalCount.error = null
                    } else {
                        editTextAnimalCount.error = "0~100 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAnimalCount.error = null
                }
            }
            // detail
            viewModel.animalDetail.observe(viewLifecycleOwner) { detail ->
                if (detail != null && detail.isNotEmpty()) {
                    if (detail.length in 2..10) {
                        editTextAnimalDetail.error = null
                    } else {
                        editTextAnimalDetail.error = "1~10 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAnimalDetail.error = null
                }
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // 동물 버튼 클릭 설정
            // Lion
            buttonAddLion.apply {
                setOnClickListener { lion ->
                    when (lion.id) {
                        R.id.button_add_lion -> {
                            editTextAnimalName.text.toString()
                            editTextAnimalName.hint = "이름"
                            editTextAnimalAge.text.toString()
                            editTextAnimalAge.hint = "나이"
                            editTextAnimalCount.text.toString()
                            editTextAnimalCount.hint = "털의 갯수"
                            editTextAnimalDetail.text.toString()
                            editTextAnimalDetail.hint = "성별(암컷 또는 수컷)"
                            textViewSelectAnimal.text = "사자"
                            editTextAnimalName.visibility = View.VISIBLE
                            editTextAnimalAge.visibility = View.VISIBLE
                            editTextAnimalCount.visibility = View.VISIBLE
                            editTextAnimalDetail.visibility = View.VISIBLE
                        }
                    }
                }
            }

            // Tiger
            buttonAddTiger.apply {
                setOnClickListener { tiger ->
                    when (tiger.id) {
                        R.id.button_add_tiger -> {
                            editTextAnimalName.text.toString()
                            editTextAnimalName.hint = "이름"
                            editTextAnimalAge.text.toString()
                            editTextAnimalAge.hint = "나이"
                            editTextAnimalCount.text.toString()
                            editTextAnimalCount.hint = "줄무늬 갯수"
                            editTextAnimalDetail.text.toString()
                            editTextAnimalDetail.hint = "몸무게"
                            textViewSelectAnimal.text = "호랑이"
                            editTextAnimalName.visibility = View.VISIBLE
                            editTextAnimalAge.visibility = View.VISIBLE
                            editTextAnimalCount.visibility = View.VISIBLE
                            editTextAnimalDetail.visibility = View.VISIBLE
                        }
                    }
                }
            }

            // Giraffe
            buttonAddGiraffe.apply {
                setOnClickListener { giraffe ->
                    when (giraffe.id) {
                        R.id.button_add_giraffe -> {
                            editTextAnimalName.text.toString()
                            editTextAnimalName.hint = "이름"
                            editTextAnimalAge.text.toString()
                            editTextAnimalAge.hint = "나이"
                            editTextAnimalCount.text.toString()
                            editTextAnimalCount.hint = "목의 길이"
                            editTextAnimalDetail.text.toString()
                            editTextAnimalDetail.hint = "달리는 속도"
                            textViewSelectAnimal.text = "기린"
                            editTextAnimalName.visibility = View.VISIBLE
                            editTextAnimalAge.visibility = View.VISIBLE
                            editTextAnimalCount.visibility = View.VISIBLE
                            editTextAnimalDetail.visibility = View.VISIBLE
                        }
                    }
                }
            }

            // Toolbar 설정
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // snackbar message 출력
                    Snackbar.make(binding.root, "입력하신 정보가 저장되지 않았습니다!!!", Snackbar.LENGTH_LONG)
                        .show()
                    // 뒤로 가기
                    removeFragment()
                }
                // menu 설정
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitems_item_complete -> {
                            // 유효성 검사
                            val result = validateInput()
                            if (result) {
                                // Data 저장
                                saveData()
                                // snackbar message 출력
                                Snackbar.make(
                                    binding.root,
                                    "동물 정보가 등록되었습니다.",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .show()
                            } else {
                                popErrorDialog()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // 유효성 검사
    fun validateInput(): Boolean {
        // 입력 요소 가져오기
        val name = viewModel.animalName.value ?: ""
        val age = viewModel.animalAge.value ?: ""
        val count = viewModel.animalCount.value ?: ""
        val detail = viewModel.animalDetail.value ?: ""

        // Animal name
        if (name.isEmpty() || name.length < 2 || name.length > 7) {
            return false
        }

        // Animal age
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }

        // Animal count
        if (count.isEmpty() || count.toIntOrNull() == null || count.toInt() < 1 || count.toInt() > 100) {
            return false
        }

        // Animal detail
        if (detail.isEmpty() || detail.length < 2 || detail.length > 10) {
            return false
        }

        return true

    }

    // Data 저장
    private fun saveData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // animalIdx 불러오기
                val zooSequence = withContext(Dispatchers.IO) { AddDao.getSequence() }
                // DB에 Sequence 업데이트
                withContext(Dispatchers.IO) { AddDao.updateSequence(zooSequence + 1) }

                // Index Save
                val zooIdx = zooSequence + 1
                // Animal Type
                val type = viewModel.animalType.value ?: ""
                // 입력 요소 Upload
                val name = viewModel.animalName.value ?: ""
                val age = viewModel.animalAge.value!!.toInt()
                val count = viewModel.animalCount.value!!.toInt()
                val detail = viewModel.animalDetail.value ?: ""

                // 저장 데이터 만들기
                val data = ZooInfo(zooIdx, type, name, age, count, detail)

                // 정보 저장
                withContext(Dispatchers.IO) { AddDao.saveAnimalData(data) }

                // 뒤로가기
                removeFragment()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Error Dialog 설정
    fun popErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("다시 확인 해주세요")
            .setMessage("모든 정보가 입력되지 않았습니다!")
            // 아이콘 설정
            .setPositiveButtonIcon(ContextCompat.getDrawable(requireContext(), R.drawable.animal))
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // 뒤로 가기 설정
    private fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }


}