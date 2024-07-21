package com.example.android_review05_baek08102.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.MenuInflater
import androidx.core.view.contains
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review05_baek08102.model.StudentData
import com.example.android_review05_baek08102.R
import com.example.android_review05_baek08102.dao.StudentDao
import com.example.android_review05_baek08102.dao.StudentDao.Companion.deleteData
import com.example.android_review05_baek08102.databinding.RowMainBinding
import com.example.android_review05_baek08102.fragment.MainFragment
import com.example.android_review05_baek08102.fragment.ShowFragment
import com.example.android_review05_baek08102.utils.FragmentName
import com.example.android_review05_baek08102.viewmodel.ShowViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomAdapter(
    private val dataList: ArrayList<StudentData>,
    private val fragmentManager: FragmentManager,
    private val menuInflater: MenuInflater,
    private val viewModel: ShowViewModel
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    class CustomViewHolder(
        rowBinding: RowMainBinding,
        menuInflater: MenuInflater,
        mainFragment: MainFragment
    ) :
        RecyclerView.ViewHolder(rowBinding.root) {


        val rowBinding: RowMainBinding
        val viewModel = ShowViewModel()

        init {
            this.rowBinding = rowBinding

            rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                menuInflater.inflate(R.menu.menu_recycler_item, menu)

                menu?.findItem(R.id.menu_recyclerItem_delete)?.setOnMenuItemClickListener {

                    val position = adapterPosition

                    viewModel.deleteDataOnPosition(position)

                    Log.d("deleting data process", "in Adapter -> clicked position : $position")

                    true
                }

            }
        }

        fun clickListener(
            viewModel: ShowViewModel,
            fragmentManager: FragmentManager,
            index: Int
        ) {
            viewModel.updateSelectedIndex(index)

            fragmentManager.beginTransaction()
                .replace(R.id.main_containerView, ShowFragment())
                .addToBackStack(FragmentName.Show_Fragment.name)
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val rowBinding = RowMainBinding.inflate(LayoutInflater.from(parent.context))
        val viewHolder = CustomViewHolder(rowBinding, menuInflater, MainFragment())

        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.rowBinding.mainRowName.text = "이름 : ${dataList[position].name}"
        holder.rowBinding.mainRowGrade.text = "학년 : ${dataList[position].grade}"

        holder.itemView.setOnClickListener {
            holder.clickListener(viewModel, fragmentManager, dataList[position].studentIdx)
        }
    }

}