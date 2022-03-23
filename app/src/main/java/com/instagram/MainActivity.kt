package com.instagram

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.instagram.databinding.ActivityMainBinding
import com.instagram.model.Post

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels() { ViewModelFactory(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root


        // 어댑터 연결
        // 이 부분은 compat? 그걸로 feed랑 post랑 어댑터 연결하기. 일단은 post만 만들어보자구.
        val itemPostAdapter = ItemPostAdapter()
        binding.rvMainPost.adapter = itemPostAdapter

        viewModel.main.observe(this, Observer {
            itemPostAdapter.submitList(it.post)
        })


        setContentView(view)
    }


}