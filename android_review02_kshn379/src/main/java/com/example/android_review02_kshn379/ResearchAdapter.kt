package com.example.android_review02_kshn379

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResearchAdapter(private val researchList: MutableList<Research>) : RecyclerView.Adapter<ResearchAdapter.ResearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ResearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResearchViewHolder, position: Int) {
        val research = researchList[position]
        holder.item.setImageResource(research.item)
        holder.name.text = research.name
        holder.word.text = research.word
    }

    override fun getItemCount(): Int {
        return researchList.size
    }

    class ResearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: ImageView = itemView.findViewById(R.id.rv_search)
        val name: TextView = itemView.findViewById(R.id.rv_name)
        val word: TextView = itemView.findViewById(R.id.rv_word)
    }
}
