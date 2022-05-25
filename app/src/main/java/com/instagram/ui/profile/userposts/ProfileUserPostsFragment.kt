package com.instagram.ui.profile.userposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentProfileUserPostsBinding
import com.instagram.ui.profile.ProfileViewModel

class ProfileUserPostsFragment(private val userUid: String): Fragment() {
    lateinit var binding: FragmentProfileUserPostsBinding
    private val viewModel: ProfileViewModel by viewModels { ViewModelFactory(requireContext(), userUid) }

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
        val userPostsAdapter = ProfileUserPostsAdapter()
        binding.rvUserPosts.adapter = userPostsAdapter

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            // .asReversed() -> grid layout에서 역순 처리하면 이상하게 처리 되므로
            // 소스 코드를 통해서 역순으로 만들어준다.
            // userPostsAdapter.submitList(profile.userPosts.asReversed())
        }


    }
}