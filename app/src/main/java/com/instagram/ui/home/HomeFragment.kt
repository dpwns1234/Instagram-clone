package com.instagram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels() { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 연결
        // 이 부분은 category_detail_fragment처럼 어댑터 여러개 합치는 거 따라하기.
        val mainAdapter = MainAdapter(this)
        binding.rvHome.adapter = mainAdapter
        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.post.observe(viewLifecycleOwner) { post ->
            mainAdapter.submitList(post)
        }
    }
}