package com.example.android_review01_kshn379

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(val profileList: MutableList<Profiles>) :
    RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gender = itemView.findViewById<ImageView>(R.id.iv_profile)
        val id = itemView.findViewById<TextView>(R.id.tv_id)
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val pwd = itemView.findViewById<TextView>(R.id.tv_pwd)
    }

    // 항목 최초 생성시 작동
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }


    // 재호출시 작동
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.gender.setImageResource(R.drawable.profile)
        holder.name.text = profileList[position].name
        holder.id.text = profileList[position].id
        holder.pwd.text = profileList[position].pwd

        // 비밀번호 * 표시
        val profile = profileList[position]
        holder.pwd.text = "*".repeat(profile.pwd.length)
    }

    // 얼마나 만들 것인지
    override fun getItemCount(): Int {
        return profileList.size
    }
}