package com.instagram

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.instagram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels() { ViewModelFactory(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터 연결
        // 이 부분은 category_detail_fragment처럼 어댑터 여러개 합치는 거 따라하기.
        val mainAdapter = MainAdapter()
        binding.rvMain.adapter = mainAdapter

        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = this

        // observe, submitList 제대로 공부 (submitList 는 어떤걸 호출하는거지??)
        // 처음에 null이기 때문에 오류가 발생하는 것 아닐까?? 블로그 찾아보기
        viewModel.post.observe(this) {
            mainAdapter.submitList(it)
        }
    }

}