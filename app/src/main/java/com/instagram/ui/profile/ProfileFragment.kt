package com.instagram.ui.profile

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat
import androidx.core.graphics.green
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.R
import com.instagram.databinding.FragmentProfileBinding
import com.instagram.databinding.FragmentProfilePostsBinding
import com.instagram.model.Profile
import com.instagram.ui.EditProfileActivity

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel = ProfileViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profile.observe(viewLifecycleOwner) {
            binding.profile = it
        }
        binding.lifecycleOwner = viewLifecycleOwner

        setViewpager()
        setEditProfileButton()
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

    private fun setEditProfileButton() {
        binding.buttonEditProfile.setOnClickListener {
            // button animation
            val clickAnimation = AlphaAnimation(1F, 0.8F)
            it.startAnimation(clickAnimation)

            startActivity(Intent(activity, EditProfileActivity::class.java))
        }
    }
}