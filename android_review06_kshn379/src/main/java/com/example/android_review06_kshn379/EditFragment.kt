package com.example.android_review06_kshn379

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.android_review06_kshn379.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addViewModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View 설정
        settingView()
        // Event 설정
        settingEvent()
        // Error 설정
        setError()

    }

    // View 설정
    private fun settingView() {
        binding.apply {
            // toolbar 설정
            toolBarEdit.apply {
                inflateMenu(R.menu.menu_rename)
            }
        }

    }

    // Error 설정
    private fun setError() {
        binding.apply {
            // name
            viewModel.animalName.observe(viewLifecycleOwner) { name ->
                if (name != null && name.isNotEmpty()) {
                    if (name.length in 2..7) {
                        textViewEditName.error = null
                    } else {
                        textViewEditName.error = "동물 이름은 6자 이하로 입력 하세요"
                    }
                } else {
                    textViewEditName.error = null
                }
            }
            // age
            viewModel.animalAge.observe(viewLifecycleOwner) { age ->
                if (age != null && age.isNotEmpty()) {
                    if (age.toInt() in 1..100) {
                        textViewEditAge.error = null
                    } else {
                        textViewEditAge.error = "동물 나이는 100살 이하로 입력해 주세요"
                    }
                } else {
                    textViewEditAge.error = null
                }
            }

            // count
            viewModel.animalCount.observe(viewLifecycleOwner) { count ->
                if (count != null && count.isNotEmpty()) {
                    if (count.toInt() in 1..100) {
                        textViewEditCount.error = null
                    } else {
                        textViewEditCount.error = "0~100 사이의 값을 입력해 주세요"
                    }
                } else {
                    textViewEditCount.error = null
                }
            }
            // detail
            viewModel.animalDetail.observe(viewLifecycleOwner) { detail ->
                if (detail != null && detail.isNotEmpty()) {
                    if (detail.length in 2..10) {
                        textViewEditDetail.error = null
                    } else {
                        textViewEditDetail.error = "1~10 사이의 값을 입력해 주세요"
                    }
                } else {
                    textViewEditDetail.error = null
                }
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // toolbar 설정
            toolBarEdit.apply {
                // navigationIcon 설정
                setNavigationOnClickListener {
                    // SnackBar Message 출력
                    Snackbar.make(binding.root, "정보가 수정되지 않았습니다!", Snackbar.LENGTH_SHORT).show()
                    // 뒤로 가기
                    removeFragment()
                }
                // menu 설정
                setOnMenuItemClickListener { rename ->
                    when (rename.itemId) {
                        R.id.menuitem_rename_edit -> {
                            // 유효성 검사
                            val check = editInput()
                            if (check) {
                                // Edit Data 저장
                                editData()
                                // SnackBar Message 출력
                                Snackbar.make(
                                    binding.root,
                                    "동물 정보가 변경 되었습니다",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                // 뒤로 가기
                                removeFragment()
                            } else {
                                editErrorDialog()
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // RecyclerView Item 데이터 수정 유효성 검사
    private fun editInput(): Boolean {
        // 입력 요소 가져오기
        val animalName = viewModel.animalName.value ?: ""
        val animalAge = viewModel.animalAge.value ?: ""
        val animalCount = viewModel.animalCount.value ?: ""
        val animalDetail = viewModel.animalDetail.value ?: ""
        Log.d("EditFragment", "animalName : ${animalName}")
        Log.d("EditFragment", "animalAge : ${animalAge}")
        Log.d("EditFragment", "animalCount : ${animalCount}")
        Log.d("EditFragment", "animalDetail : ${animalDetail}")

        // name
        if (animalName.isEmpty() || animalName.length < 2 || animalName.length > 7) {
            return false
        }
        // Animal age
        if (animalAge.isEmpty() || animalAge.toIntOrNull() == null || animalAge.toInt() < 1 || animalAge.toInt() > 100) {
            return false
        }

        // Animal count
        if (animalCount.isEmpty() || animalCount.toIntOrNull() == null || animalCount.toInt() < 1 || animalCount.toInt() > 100) {
            return false
        }

        // Animal detail
        if (animalDetail.isEmpty() || animalDetail.length < 2 || animalDetail.length > 10) {
            return false
        }

        return true

    }

    // Error Dialog 설정
    private fun editErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("정보 수정 오류")
            .setMessage("수정 하실 정보를 다시 확인 해주세요")
            // 아이콘 설정
            .setPositiveButtonIcon(ContextCompat.getDrawable(requireContext(), R.drawable.animal))
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // Edit Data 저장
    private fun editData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val zooSequence = viewModel.zooIdx.value!!.toString().toInt()

                val animalType = viewModel.animalType.value ?: ""
                val animalName = viewModel.animalName.value ?: ""
                val animalAge = viewModel.animalAge.value!!.toString().toInt()
                val animalCount = viewModel.animalCount.value!!.toString().toInt()
                val animalDetail = viewModel.animalDetail.value ?: ""

                val editData =
                    ZooInfo(
                        zooSequence,
                        animalType,
                        animalName,
                        animalAge,
                        animalCount,
                        animalDetail,
                        true
                    )

                withContext(Dispatchers.IO) { AddDao.updateEditData(editData) }

                val position = arguments?.getInt("position") ?: -1
                viewLifecycleOwner.lifecycle.apply {
                    viewModel.getData(position)
                }

                // Back
                removeFragment()

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }

    }

    // 뒤로 가기 설정
    private fun removeFragment() {
        parentFragmentManager.popBackStack()
    }
}