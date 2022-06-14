package com.instagram.ui.userprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.common.ViewModelFactory
import com.instagram.databinding.FragmentUserProfileBinding
import com.instagram.model.Profile
import com.instagram.model.User
import com.instagram.ui.profile.ProfileViewModel
import com.instagram.ui.profile.ProfileViewpagerAdapter

class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val args: UserProfileFragmentArgs by navArgs()
    private val myUid = Firebase.auth.currentUser!!.uid
    private val database = Firebase.database(firebaseUrl)

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

        val userUid = args.userUid
        val viewModel = UserProfileViewModel(userUid)

        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            binding.profile = profile
            setButtonFollowUI(profile.followerList)
        }
        setButtonFollow(userUid)
        setViewpager(userUid)
    }

    private fun setButtonFollow(userUid: String) {
        binding.buttonFollowAndCancel.setOnClickListener {
            val databaseMyRef =
                database.getReference("users/$myUid/following_list")        // 내 입장에선 팔로잉
            val databaseUserRef =
                database.getReference("users/$userUid/follower_list")     // 유저 입장에선 팔로워

//            databaseMyRef.get().addOnSuccessListener { snapshot ->
//                val followingList = snapshot.getValue<MutableList<String>>() ?: mutableListOf()
//                databaseUserRef.runTransaction(object : Transaction.Handler {
//                    override fun doTransaction(currentData: MutableData): Transaction.Result {
//                        val followerList =
//                            currentData.getValue<MutableList<String>>() ?: mutableListOf()
//                        // 이미 follow 상태라면
//                        if (followerList.contains(myUid)) {
//                            Log.d("hihi", "already exist")
//                            // 해당 유저의 팔로워 리스트에서 자신의 uid를 지우고
//                            followerList.remove(myUid)
//                            // 자신의 팔로잉 리스트에서 해당 유저의 uid를 지운다.
//                            followingList.remove(userUid)
//                        } else {
//                            followerList.add(myUid)
//                            followingList.add(userUid)
//                        }
//
//                        currentData.value = followerList
//                        database.getReference("users/$myUid/following_list").setValue(followerList)
//                        return Transaction.success(currentData)
//                    }
//
//                    override fun onComplete(
//                        error: DatabaseError?, committed: Boolean,
//                        currentData: DataSnapshot?,
//                    ) {
//                        Toast.makeText(requireContext(), "팔로잉 하셨습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            }

            // change
            val databaseMyRef2 =
                database.getReference("users/$myUid/profiles/following_list")        // 내 입장에선 팔로잉
            val databaseUserRef2 =
                database.getReference("users/$userUid/profiles")     // 유저 입장에선 팔로워

            databaseMyRef2.get().addOnSuccessListener { snapshot ->
                val followingList = snapshot.getValue<MutableList<String>>() ?: mutableListOf()
                Log.d("hihi", "follower : $followingList")
                databaseUserRef2.runTransaction(object : Transaction.Handler {
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        val profileValue =
                            currentData.getValue<Profile>() ?: return Transaction.success(currentData)
                        val followerList = profileValue.followerList
                        setButtonFollowUI(followerList)

                        // 이미 follow 상태라면
                        if (followerList.contains(myUid)) {
                            // 해당 유저의 팔로워 리스트에서 자신의 uid를 지우고
                            followerList.remove(myUid)
                            // 자신의 팔로잉 리스트에서 해당 유저의 uid를 지운다.
                            followingList.remove(userUid)
                        } else {
                            followerList.add(myUid)
                            followingList.add(userUid)
                        }

                        currentData.value = profileValue
                        database.getReference("users/$myUid/profiles/following_list").setValue(followingList)
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(
                        error: DatabaseError?, committed: Boolean,
                        currentData: DataSnapshot?,
                    ) {
                        Toast.makeText(requireContext(), "팔로잉 하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun setButtonFollowUI(followerList: MutableList<String>) {
        if (followerList.contains(myUid)) {
            with(binding.buttonFollowAndCancel) {
                this.text = "팔로잉"
                this.setTextColor(resources.getColor(R.color.black))
                this.background = resources.getDrawable(R.drawable.button_round)
            }
        } else {
            with(binding.buttonFollowAndCancel) {
                this.text = "팔로우"
                this.setTextColor(resources.getColor(R.color.white))
                this.background = resources.getDrawable(R.drawable.button_round_blue)
            }
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