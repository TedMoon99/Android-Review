package com.example.android_review02_kshn379

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var inputName: EditText
    private lateinit var checkAddButton: Button
    private lateinit var inputWord: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var researchAdapter: ResearchAdapter

    private var researchList = mutableListOf<Research>()

    private val searchList = arrayListOf(
        Research(R.drawable.ic_launcher_foreground, "name1", "word1"),
        Research(R.drawable.ic_launcher_foreground, "name2", "word2"),
        Research(R.drawable.ic_launcher_foreground, "name3", "word3")
    )

    private val displayedSearchList = arrayListOf<Research>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputName = findViewById(R.id.input_name)
        checkAddButton = findViewById(R.id.check_add)
        inputWord = findViewById(R.id.input_word)
        recyclerView = findViewById(R.id.search_item)

        researchAdapter = ResearchAdapter(researchList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = researchAdapter

        checkAddButton.setOnClickListener {
            val name = inputName.text.toString().trim()
            val word = inputWord.text.toString().trim()

            if (name.isNotEmpty()) {
                researchList.clear()
                researchList.addAll(searchList)
                researchAdapter.notifyDataSetChanged()
                inputName.text.clear()
            } else if (word.isNotEmpty()) {
                filterList(word)
                inputWord.text.clear()
            }
        }
    }

    private fun filterList(s: String) {

    }
}



