package com.instagram.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.instagram.R
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentHomeBinding
import com.instagram.ui.home.feed.FeedAdapter
import com.instagram.ui.produce.ProduceActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels() { ViewModelFactory(requireContext(), "22") }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setPopupMenu()
    }

    private fun setData() {
        val feedAdapter = FeedAdapter()
        binding.rvFeed.adapter = feedAdapter
        val homeAdapter = HomeAdapter(this, this)
        binding.rvHome.adapter = homeAdapter
        // TODO. 공부: 이거 왜 해야하는지, 어떻게 해야하는 지 공뿌
        binding.lifecycleOwner = viewLifecycleOwner

        with(viewModel) {
            feed.observe(viewLifecycleOwner) { feed ->
                feedAdapter.submitList(feed)
            }
            post.observe(viewLifecycleOwner) { post ->
                homeAdapter.submitList(post)
            }
        }
    }

    private fun setPopupMenu() {
        binding.buttonPlus.setOnClickListener {
            val plusMenu = PopupMenu(context, it)
            plusMenu.menuInflater.inflate(R.menu.plus_menu, plusMenu.menu)
            plusMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_produce_post -> {
                        Toast.makeText(requireContext(), "post 선택", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity, ProduceActivity::class.java))
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
}