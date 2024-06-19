package com.example.android_review01_baek08102

import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_review01_baek08102.databinding.ActivityMainBinding
import com.example.android_review01_baek08102.databinding.ItemMainBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val datalist: MutableList<Profile> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingEvent()
        settingView()
    }

    fun settingEvent() {

        binding.apply {
            confirmButton.setOnClickListener {
                val id = textId.text.toString()
                val name = textName.text.toString()

                if (id.isNotEmpty() && name.isNotEmpty()) {
                    val profile = Profile(id, name)

                    datalist.add(profile)
                    Log.d("동수", "$id $name $profile")
                    recyclerView.adapter?.notifyItemInserted(datalist.size - 1)

                    textId.text = null
                    textName.text = null
                }
            }
        }
    }

    fun settingView() {

        binding.apply {

            val deco = MaterialDividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            ).apply {
                isLastItemDecorated = false
            }

            recyclerView.apply {
                this.adapter = MyAdapter(datalist)
                this.layoutManager = LinearLayoutManager(this@MainActivity)

                addItemDecoration(deco)
            }
        }
    }
}