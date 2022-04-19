package com.instagram.ui.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        setViewpager()
    }

    private fun setViewpager() {
        val profileViewpagerAdapter = ProfileViewpagerAdapter(requireActivity())
        with(binding.viewpagerProfile) {
            adapter = profileViewpagerAdapter
            val tabsIconDrawable = arrayOf(R.drawable.gallery, R.drawable.user_posts_icon)

            TabLayoutMediator(binding.indicatorProfile, this) { tab, position ->
                tab.icon = ContextCompat.getDrawable(
                    this@ProfileFragment.requireContext(),
                    tabsIconDrawable[position]
                )

            }.attach()
        }
    }
}