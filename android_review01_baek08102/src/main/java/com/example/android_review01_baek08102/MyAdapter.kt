package com.example.android_review01_baek08102

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review01_baek08102.databinding.ItemMainBinding

class MyAdapter(private val profileList: List<Profile>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView
        val name: TextView

        init {
            id = view.findViewById(R.id.id_data)
            name = view.findViewById(R.id.name_data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        Log.d("동수", "어댑터 생성")
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.id.text = "ID : ${profileList[position].id}"
            holder.name.text = "이름 : ${profileList[position].name}"
    }
}
