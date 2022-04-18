package com.instagram.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.green
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.R
import com.instagram.databinding.FragmentProfileBinding
import com.instagram.databinding.FragmentProfilePostsBinding
import com.instagram.model.Profile

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileViewModel = ProfileViewModel(requireContext())
        binding.profile = profileViewModel.profile.value
        binding.lifecycleOwner = viewLifecycleOwner


        // TODO. viewpager로 연결
        val profileViewpagerAdapter = ProfileViewpagerAdapter(requireActivity())
//        profileViewpagerAdapter.addFragment(ProfilePostsFragment(profileViewModel))
//        profileViewpagerAdapter.addFragment(ProfileUserPostsFragment(profileViewModel))

        with(binding.viewpagerProfile) {
            adapter = profileViewpagerAdapter
            TabLayoutMediator(binding.indicatorProfile, this) { tab, position ->

            }.attach()
            Log.d("play?", "마지막에")
        }


    }
}