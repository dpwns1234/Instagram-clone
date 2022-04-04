package com.instagram

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.instagram.databinding.ActivityMainBinding
import com.instagram.databinding.ItemPostBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postBinding: ItemPostBinding

    private val viewModel: MainViewModel by viewModels() { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postBinding = ItemPostBinding.inflate(layoutInflater)
        
        // 어댑터 연결
        // 이 부분은 category_detail_fragment처럼 어댑터 여러개 합치는 거 따라하기.
        val mainAdapter = MainAdapter(this)
        binding.rvMain.adapter = mainAdapter
        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = this

        viewModel.post.observe(this) { post ->
            mainAdapter.submitList(post)
        }

    }

}