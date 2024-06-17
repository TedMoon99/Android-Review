package com.example.android_review01_kshn379

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.example.android_review01_kshn379.databinding.CustomToastBinding

fun Context.showCustomToast(message: String) {
    val inflater = LayoutInflater.from(this)
    val binding = CustomToastBinding.inflate(inflater)

    binding.textView.text = message

    with(Toast(this)) {
        duration = Toast.LENGTH_LONG
        view = binding.root
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}
class ProfileAdapter(val profileList: ArrayList<Profiles>) : RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                val profile: Profiles = profileList[curPos]
                it.context.showCustomToast("이름: ${profile.name}\n아이디: ${profile.id}\n비밀번호: ${profile.pwd}")
            }
        }
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.gender.setImageResource(profileList.get(position).gender)
        holder.name.text = profileList.get(position).name
        holder.id.text = profileList.get(position).id
        holder.pwd.text = profileList.get(position).pwd

        // 비밀번호 * 표시
        val profile = profileList[position]
        holder.pwd.text = "*".repeat(profile.pwd.length)
    }

    override fun getItemCount(): Int {
        return profileList.size
    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gender = itemView.findViewById<ImageView>(R.id.iv_profile)
        val id = itemView.findViewById<TextView>(R.id.tv_id)
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val pwd = itemView.findViewById<TextView>(R.id.tv_pwd)
    }

}

class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        c.drawBitmap(
            BitmapFactory.decodeResource(context.getResources(), R.drawable.profile),
            0f, 0f, null)
    }
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        // 뷰 크기 계산
        val width = parent.width
        val height = parent.height
        // 이미지 크기 계산
        val dr: Drawable? = ResourcesCompat.getDrawable(context.getResources(),
            R.drawable.ic_launcher_foreground, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight
        // 이미지가 그려질 위치 계산
        val left = width / 2 - drWidth?.div(2) as Int
        val top = height / 2 - drHeight?.div(2) as Int
        c.drawBitmap(
            BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1
        if (index % 3 == 0)
            outRect.set(10, 10, 10, 60) // left, top, right, bottom
        else
            outRect.set(10, 10, 10 ,0)
        view.setBackgroundColor(Color.LTGRAY)
        ViewCompat.setElevation(view, 20.0f)
//        binding.recyclerView.addItemDecoration(MyDecoration(this))
    }

}