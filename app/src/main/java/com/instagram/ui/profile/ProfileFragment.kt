package com.instagram.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentProfileBinding
import com.instagram.network.ApiClient
import com.instagram.ui.login.LoginActivity

// TODO. 왜 observe가 제대로 반응을 안하지? 뒤로가기 버튼이나 나갔다 들어와야 데이터가 변경된게 반영이됨..
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val auth = Firebase.auth
    private val userUid = auth.currentUser!!.uid
    private val profileViewModel: ProfileViewModel by viewModels { ViewModelFactory(requireContext(), userUid) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profile.observe(viewLifecycleOwner) {
            binding.profile = it
        }
        binding.lifecycleOwner = viewLifecycleOwner
        setViewpager(userUid)
        setEditProfileButton()
        setSignOutButton()
    }

    private fun setViewpager(userUid: String) {
        val profileViewpagerAdapter = ProfileViewpagerAdapter(requireActivity(), userUid)
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

    private fun setSignOutButton() {
        binding.buttonSignOut.setOnClickListener {
            // again request by using alert message
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Instagram에서 로그아웃 하시겠서요?")
                .setNegativeButton("취소") { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton("로그아웃") { dialog, which ->
                    // firebase sign out
                    auth.signOut()
                    Toast.makeText(requireContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

                    // move login activity
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }.show()
        }
    }

}