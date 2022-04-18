package com.instagram.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.instagram.databinding.FragmentProfilePostsBinding
import com.instagram.model.PreviewPost

class ProfilePostsFragment(): Fragment() {
    lateinit var binding: FragmentProfilePostsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ProfileViewModel(requireContext())
        val postsAdapter = ProfilePostsAdapter()
        binding.rvPost.adapter = postsAdapter

        viewModel.profilePosts.observe(viewLifecycleOwner) { posts ->
            postsAdapter.submitList(posts)
        }

    }
}