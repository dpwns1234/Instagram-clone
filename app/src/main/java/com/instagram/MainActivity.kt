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
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels() { ViewModelFactory(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 연결
        // 이 부분은 category_detail_fragment처럼 어댑터 여러개 합치는 거 따라하기.
        val itemPostAdapter = ItemPostAdapter()
        binding.rvMain.adapter = itemPostAdapter

        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = this

        viewModel.post.observe(this, Observer {
            itemPostAdapter.submitList(it)
        })

    }

}