package com.instagram.ui.profile.userposts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.FragmentProfileUserPostsBinding
import com.instagram.ui.profile.ProfileViewModel

class ProfileUserPostsFragment(private val userUid: String): Fragment() {
    lateinit var binding: FragmentProfileUserPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileUserPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ProfileViewModel(userUid)
        val userPostsAdapter = ProfileUserPostsAdapter()
        binding.rvUserPosts.adapter = userPostsAdapter

        viewModel.profileUserPosts.observe(viewLifecycleOwner) { userPosts ->
            userPostsAdapter.submitList(userPosts)
        }


    }
}