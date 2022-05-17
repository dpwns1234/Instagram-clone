package com.instagram.ui.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.instagram.R
import com.instagram.databinding.FragmentUserProfileBinding
import com.instagram.model.Profile
import com.instagram.ui.profile.ProfileViewModel
import com.instagram.ui.profile.ProfileViewpagerAdapter

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        arguments?.let { bundle ->
            val userUid = bundle.getString("userUid")
            val viewModel = ProfileViewModel(userUid!!)
            viewModel.profile.observe(viewLifecycleOwner) { profile ->
                binding.profile = profile
            }

            setViewpager(userUid)
        }
    }

    private fun setViewpager(userUid: String) {
        with(binding.viewpagerProfile) {
            val profileViewpagerAdapter = ProfileViewpagerAdapter(requireActivity(), userUid)
            adapter = profileViewpagerAdapter
            val tabsIconDrawable = arrayOf(R.drawable.gallery, R.drawable.user_posts_icon)

            TabLayoutMediator(binding.indicatorProfile, this) { tab, position ->
                tab.icon = ContextCompat.getDrawable(
                    this@UserProfileFragment.requireContext(),
                    tabsIconDrawable[position]
                )

            }.attach()
        }
    }
}