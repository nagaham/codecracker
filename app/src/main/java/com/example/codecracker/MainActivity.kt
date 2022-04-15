package com.example.codecracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.example.codecracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.spinner01.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()) binding.textView01.text = it
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinner02.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()) binding.textView02.text = it
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinner03.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()) binding.textView03.text = it
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinner04.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner = parent as? Spinner
                val item = spinner?.selectedItem as? String
                item?.let {
                    if (it.isNotEmpty()) binding.textView04.text = it
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}