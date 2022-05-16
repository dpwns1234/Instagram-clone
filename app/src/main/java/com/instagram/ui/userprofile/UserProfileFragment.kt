package com.instagram.ui.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.instagram.databinding.FragmentUserProfileBinding
import com.instagram.model.Profile
import com.instagram.ui.profile.ProfileViewModel

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val userUid = bundle.getString("userUid")
            val viewModel = UserProfileViewModel(userUid!!)
            viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
                binding.profile = profile
            }
        }
    }
}