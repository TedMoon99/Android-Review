package com.example.android_review01_tedmoon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: ArrayList<List<String>>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val textView_id: TextView
        val textView_name: TextView

        init {
            // row xml과 연결
            textView_id = view.findViewById(R.id.textView_row_main_id)
            textView_name = view.findViewById(R.id.textView_row_main_name)
        }

    }

    // Adpater 최초 생성 시에 실행됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // row xml 생성
        val rowView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_main, parent, false)

        // ViewHodler 클래스에 row xml을 담아서 반환
        return ViewHolder(rowView)
    }

    // 만들어줄 Adapter 갯수 반환
    override fun getItemCount(): Int {
        return dataSet.size
    }

    // Adapter 화면이 다시 호출될 때 실행됨
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView_id.text = dataSet[position][0] // 아이디 전송
        holder.textView_name.text = dataSet[position][1] // 이름 전송
    }
}