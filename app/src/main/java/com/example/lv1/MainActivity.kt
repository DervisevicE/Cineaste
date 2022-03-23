package com.example.lv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var button: Button
    private lateinit var editText: EditText

    private val listaVrijednosti = arrayListOf<String>()
    private lateinit var adapter: MyArrayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button1)
        editText = findViewById(R.id.editText1)
        listView = findViewById(R.id.listView1)

        adapter = MyArrayAdapter(this, android.R.layout.simple_list_item_1, listaVrijednosti)
        listView.adapter = adapter

        button.setOnClickListener{
            addToList()
        }
    }

    private fun addToList() {
        listaVrijednosti.add(0,editText.text.toString())
        adapter.notifyDataSetChanged()
        editText.setText("")
    }
}