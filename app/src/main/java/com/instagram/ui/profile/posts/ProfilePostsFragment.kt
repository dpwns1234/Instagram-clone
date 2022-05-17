package com.instagram.ui.profile.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.FragmentProfilePostsBinding
import com.instagram.ui.profile.ProfileViewModel

class ProfilePostsFragment(private val userUid: String): Fragment() {
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
        val viewModel = ProfileViewModel(userUid)
        val postsAdapter = ProfilePostsAdapter()

        binding.rvPost.adapter = postsAdapter
        viewModel.profilePosts.observe(viewLifecycleOwner) { posts ->
            postsAdapter.submitList(posts.asReversed())
        }

    }

}