package com.instagram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.instagram.R
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentHomeBinding
import com.instagram.ui.FeedAdapter

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
        // TODO. 그냥 따로 따로 adapter 연결하면 되지 않나??
        val feedAdapter = FeedAdapter()
        val homeAdapter = HomeAdapter(this)

        binding.rvFeed.adapter = feedAdapter
        binding.rvHome.adapter = homeAdapter
        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = viewLifecycleOwner

        setData(feedAdapter, homeAdapter)

        binding.buttonPlus.setOnClickListener {
            val plusMenu = PopupMenu(context, it)
            plusMenu.menuInflater.inflate(R.menu.plus_menu, plusMenu.menu)
            plusMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_produce_post -> {
                        Toast.makeText(requireContext(), "post 선택", Toast.LENGTH_SHORT).show()
                    }
                    R.id.navigation_produce_feed -> {
                        Toast.makeText(requireContext(), "feed 선택", Toast.LENGTH_SHORT).show()
                    }
                    R.id.navigation_produce_shorts -> {
                        Toast.makeText(requireContext(), "릴스 선택", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(requireContext(), "live 선택", Toast.LENGTH_SHORT).show()
                    }
                }

                return@setOnMenuItemClickListener false
            }
            plusMenu.show()
        }
    }

    private fun setData(
        feedAdapter: FeedAdapter,
        homeAdapter: HomeAdapter
    ) {
        with(viewModel) {
            feed.observe(viewLifecycleOwner) { feed ->
                feedAdapter.submitList(feed)
            }

            post.observe(viewLifecycleOwner) { post ->
                homeAdapter.submitList(post)
            }
        }
    }
}