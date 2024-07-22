package com.example.android_review05_kshn379

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.android_review05_kshn379.databinding.FragmentAddBinding
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

    // View 설정
    private fun settingView() {
        // Toolbar 설정
        binding.apply {
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_item)
            }
        }
        // 에러 설정
        settingError()
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            toolBarAdd.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // 뒤로 가기
                    removeFragment()
                }
                // Menu
                setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.menuitem_item_save -> {
                            // 유효성 검사
                            val result = validateInput()
                            if (result) {
                                // 데이터 저장
                                saveData()
                                // 스낵바 메시지 출력
                                Snackbar.make(binding.root, "등록되었습니다", Snackbar.LENGTH_SHORT).show()
                                // 뒤로 가기
                                removeFragment()
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

    // 에러 설정
    fun settingError() {
        binding.apply {
            // name
            viewModel.studentName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..5) {
                        editTextAddName.error = null
                    } else {
                        editTextAddName.error = "이름은 4자 이하로 입력"
                    }
                } else {
                    editTextAddName.error = null
                }
            }
            // 학년
            viewModel.studentGrade.observe(viewLifecycleOwner) { grade ->
                if (grade != null && grade.isNotEmpty()) {
                    if (grade.toInt() in 1..6) {
                        editTextAddGrade.error = null
                    } else {
                        editTextAddGrade.error = "학년은 1~6 사이의 학년을 입력해 주세요"
                    }
                } else {
                    editTextAddGrade.error = null
                }
            }
            // 국어 점수
            viewModel.studentKor.observe(viewLifecycleOwner) { kor ->
                if (kor != null && kor.isNotEmpty()) {
                    if (kor.toDouble() in 0.0..100.0) {
                        editTextAddKor.error = null
                    } else {
                        editTextAddKor.error = "국어 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddKor.error = null
                }
            }
            // 영어 점수
            viewModel.studentEng.observe(viewLifecycleOwner) { eng ->
                if (eng != null && eng.isNotEmpty()) {
                    if (eng.toDouble() in 0.0..100.0) {
                        editTextAddEng.error = null
                    } else {
                        editTextAddEng.error = "영어 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddEng.error = null
                }
            }
            // 수학 점수
            viewModel.studentMath.observe(viewLifecycleOwner) { math ->
                if (math != null && math.isNotEmpty()) {
                    if (math.toDouble() in 0.0..100.0) {
                        editTextAddMath.error = null
                    } else {
                        editTextAddMath.error = "수학 점수는 0점에서 100점 사이의 값을 입력해 주세요"
                    }
                } else {
                    editTextAddMath.error = null
                }
            }
        }
    }

    // 유효성 검사
    fun validateInput(): Boolean {
        // 입력 요소 가져오기
        val name = viewModel.studentName.value ?: ""
        val grade = viewModel.studentGrade.value ?: ""
        val kor = viewModel.studentKor.value ?: ""
        val eng = viewModel.studentEng.value ?: ""
        val math = viewModel.studentMath.value ?: ""

        // 이름
        if (name.isEmpty() || name.length < 2 || name.length > 5) {
            return false
        }
        // 학년
        if (grade.isEmpty() || grade.toIntOrNull() == null || grade.toInt() < 1 || grade.toInt() > 6) {
            return false
        }
        // 국어
        if (kor.isEmpty() || kor.toDoubleOrNull() == null || kor.toDouble() < 0.0 || kor.toDouble() > 100.0) {
            return false
        }
        // 영어
        if (eng.isEmpty() || eng.toDoubleOrNull() == null || eng.toDouble() < 0.0 || eng.toDouble() > 100.0) {
            return false
        }
        // 수학
        if (math.isEmpty() || math.toDoubleOrNull() == null || math.toDouble() < 0.0 || math.toDouble() > 100.0) {
            return false
        }

        return true
    }

    // Data 저장
    private fun saveData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // studentIdx 불러오기
                val studentSequence = withContext(Dispatchers.IO) { AddDao.getSequence() }
                // DB에 Sequence 업데이트
                withContext(Dispatchers.IO) { AddDao.updateSequence(studentSequence + 1) }

                // index 저장
                val studentIdx = studentSequence + 1
                // 입력 요소 불러오기
                val name = viewModel.studentName.value ?: ""
                val grade = viewModel.studentGrade.value!!.toInt()
                val kor = viewModel.studentKor.value!!.toDouble()
                val eng = viewModel.studentEng.value!!.toDouble()
                val math = viewModel.studentMath.value!!.toDouble()

                // 저장 데이터 만들기
                val data = ManageInfo(studentIdx, name, grade, kor, eng, math)

                // 사용자 정보 저장
                withContext(Dispatchers.IO) { AddDao.saveStudentData(data) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    // Dialog 설정
    fun popErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("재확인!!")
            .setMessage("모든 정보가 입력되지 않았습니다")
            // dialog : DialogInterface 타입의 매개변수
            // _ : 클릭된 버튼의 ID 두 번째 매개변수
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }


    // 입력 요소 초기화
    private fun settingInput() {
        viewModel.clearText()
    }


    // 뒤로 가기 설정
    private fun removeFragment() {
        parentFragmentManager.popBackStack(
            FragmentName.ADD_FRAGMENT.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}