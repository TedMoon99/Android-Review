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
    private val tigerViewModel: TigerViewModel by activityViewModels()
    private val lionViewModel: LionViewModel by activityViewModels()
    private val giraffeViewModel: GiraffeViewModel by activityViewModels()

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

    }


    // View 설정
    private fun settingView() {
        binding.apply {
            // 입력 요소 초기화
            lionViewModel.initInput()
            tigerViewModel.initInput()
            giraffeViewModel.initInput()

            // Toolbar 설정
            toolBarAdd.apply {
                inflateMenu(R.menu.menu_items)
            }
        }
    }

    // Event 설정
    private fun settingEvent() {
        binding.apply {
            // 화면 전환
            buttonAddLion.setOnClickListener { moveFragment(LionFragment(), "lionFragment") }
            buttonAddTiger.setOnClickListener { moveFragment(TigerFragment(), "tigerFragment") }
            buttonAddGiraffe.setOnClickListener { moveFragment(GiraffeFragment(), "giraffeFragment") }

            toolBarAdd.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.menuitems_item_complete -> {
                        if (validateInput()) {
                            saveLionData()
                        } else if (validateTiger()) {
                            saveTigerData()
                        } else if (validateGiraffe()) {
                            saveGiraffeData()
                        } else {
                            popErrorDialog()
                        }
                    }
                }
                true
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
            }
        }
    }


    // 사자 데이터 저장
    private fun saveLionData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // animalIdx 불러오기
                val zooSequence = withContext(Dispatchers.IO) { LionDao.getSequence() }
                // DB에 Sequence 업데이트
                withContext(Dispatchers.IO) { LionDao.updateSequence(zooSequence + 1) }

                // Index Save
                val zooIdx = zooSequence + 1

                // 입력 요소 Upload
                val type = "사자"
                val name = lionViewModel.lionName.value ?: ""
                val age = lionViewModel.lionAge.value!!.toInt()
                val fur = lionViewModel.lionFur.value!!.toInt()
                val gender = lionViewModel.lionGender.value!!

                // 저장 데이터 만들기
                val data = LionInfo(zooIdx, type, name, age, fur, gender)
                Log.d("LionViewModel", "SaveLion: $data")

                // 정보 저장
                withContext(Dispatchers.IO) { LionDao.saveLionData(data) }

                // ZooInfo 데이터
                val zooInfo = ZooInfo(
                    zooIdx = zooIdx,
                    animalType = "사자",
                    animalName = name,
                    animalAge = age,
                    animalCount = fur,
                    animalDetail = gender,
                    dataState = true
                )
                withContext(Dispatchers.IO) { LionDao.saveZooData(zooInfo) }
                Snackbar.make(binding.root, "사자 정보가 등록되었습니다.", Snackbar.LENGTH_SHORT)
                    .show()
                removeFragment()

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("LionViewModel", "Error Lion: ${e.message}")
            }
        }
    }

    // 호랑이 데이터 저장
    private fun saveTigerData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // animalIdx 불러오기
                val zooSequence = withContext(Dispatchers.IO) { TigerDao.getSequence() }
                // DB에 Sequence 업데이트
                withContext(Dispatchers.IO) { TigerDao.updateSequence(zooSequence + 1) }

                // Index Save
                val zooIdx = zooSequence + 1

                // 입력 요소 Upload
                val type = "호랑이"
                val name = tigerViewModel.tigerName.value ?: ""
                val age = tigerViewModel.tigerAge.value!!.toInt()
                val strip = tigerViewModel.tigerStrip.value!!.toInt()
                val weight = tigerViewModel.tigerWeight.value!!.toInt()

                // 저장 데이터 만들기
                val data = TigerInfo(zooIdx, type, name, age, strip, weight)
                Log.d("TigerViewModel", "Save Tiger: $data")

                // 정보 저장
                withContext(Dispatchers.IO) { TigerDao.saveTigerData(data) }

                // ZooInfo 데이터
                val zooInfo = ZooInfo(
                    zooIdx = zooIdx,
                    animalType = "호랑이",
                    animalName = name,
                    animalAge = age,
                    animalCount = strip,
                    animalDetail = weight.toString(),
                    dataState = true
                )
                withContext(Dispatchers.IO) { TigerDao.saveZooData(zooInfo) }
                Snackbar.make(binding.root, "호랑이 정보가 등록되었습니다.", Snackbar.LENGTH_SHORT)
                    .show()
                removeFragment()

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TigerViewModel", "Error Tiger: ${e.message}")
            }
        }
    }

    // 기린 데이터 저장
    private fun saveGiraffeData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // animalIdx 불러오기
                val zooSequence = withContext(Dispatchers.IO) { GiraffeDao.getSequence() }
                // DB에 Sequence Update
                withContext(Dispatchers.IO) { GiraffeDao.updateSequence(zooSequence + 1) }

                // Index Save
                val zooIdx = zooSequence + 1

                // 입력 요소 Upload
                val type = "기린"
                val name = giraffeViewModel.giraffeName.value ?: ""
                val age = giraffeViewModel.giraffeAge.value!!.toInt()
                val neck = giraffeViewModel.giraffeNeck.value!!.toInt()
                val run = giraffeViewModel.giraffeRun.value!!.toInt()

                // 저장 데이터 만들기
                val data = GiraffeInfo(zooIdx, type, name, age, neck, run)
                Log.d("GiraffeViewModel", "Save Giraffe: $data")

                // 정보 저장
                withContext(Dispatchers.IO) { GiraffeDao.saveGiraffeData(data) }

                // ZooInfo 데이터
                val zooInfo = ZooInfo(
                    zooIdx = zooIdx,
                    animalType = "기린",
                    animalName = name,
                    animalAge = age,
                    animalCount = neck,
                    animalDetail = run.toString(),
                    dataState = true
                )
                withContext(Dispatchers.IO) { GiraffeDao.saveZooData(zooInfo) }
                Snackbar.make(binding.root, "기린 정보가 등록되었습니다.", Snackbar.LENGTH_SHORT)
                    .show()
                removeFragment()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("GiraffeViewModel", "Error Giraffe: ${e.message}")
            }
        }
    }

    // 사자 유효성 검사
    private fun validateInput(): Boolean {
        // 입력 요소 가져오기
        val name = lionViewModel.lionName.value ?: ""
        val age = lionViewModel.lionAge.value ?: ""
        val fur = lionViewModel.lionFur.value ?: ""

        // Lion Name
        if (name.isEmpty() || name.length < 2 || name.length > 8) {
            return false
        }

        // Lion Age
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }

        // Lion Fur
        if (fur.isEmpty() || fur.toIntOrNull() == null || fur.toInt() < 1 || fur.toInt() > 100) {
            return false
        }
        return true
    }

    // 호랑이 유효성 검사
    private fun validateTiger(): Boolean {
        // 입력 요소 가져 오기
        val name = tigerViewModel.tigerName.value ?: ""
        val age = tigerViewModel.tigerAge.value ?: ""
        val strip = tigerViewModel.tigerStrip.value ?: ""
        val weight = tigerViewModel.tigerWeight.value ?: ""

        // Tiger Name
        if (name.isEmpty() || name.length < 2 || name.length > 8) {
            return false
        }

        // Tiger Age
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }

        // Tiger Strip
        if (strip.isEmpty() || strip.toIntOrNull() == null || strip.toInt() < 1 || strip.toInt() > 100) {
            return false
        }

        // Tiger Weight
        if (weight.isEmpty() || weight.toIntOrNull() == null || weight.toInt() < 1 || weight.toInt() > 100) {
            return false
        }
        return true
    }

    // 기린 유효성 검사
    private fun validateGiraffe(): Boolean {
        // 입력 요소 가져 오기
        val name = giraffeViewModel.giraffeName.value ?: ""
        val age = giraffeViewModel.giraffeAge.value ?: ""
        val neck = giraffeViewModel.giraffeNeck.value ?: ""
        val run = giraffeViewModel.giraffeRun.value ?: ""

        // Giraffe Name
        if (name.isEmpty() || name.length < 2 || name.length > 8) {
            return false
        }

        // Giraffe Age
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 1 || age.toInt() > 100) {
            return false
        }

        // Giraffe Neck
        if (neck.isEmpty() || neck.toIntOrNull() == null || neck.toInt() < 1 || neck.toInt() > 100) {
            return false
        }

        // Giraffe Run
        if (run.isEmpty() || run.toIntOrNull() == null || run.toInt() < 1 || run.toInt() > 100) {
            return false
        }
        return true
    }


    // 종류별 화면 전환
    private fun moveFragment(fragment: Fragment, tag: String) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.add_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
        binding.textViewSelectAnimal.visibility = View.INVISIBLE
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