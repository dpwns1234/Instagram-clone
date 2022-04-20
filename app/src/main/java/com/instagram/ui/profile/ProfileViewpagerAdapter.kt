package com.instagram.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.instagram.ui.profile.posts.ProfilePostsFragment
import com.instagram.ui.profile.userposts.ProfileUserPostsFragment

class ProfileViewpagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){

    private val fragments = listOf(ProfilePostsFragment(), ProfileUserPostsFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }



//    fun addFragment(fragment: Fragment) {
//        fragments.add(fragment)
//        notifyItemInserted(fragments.size -1)
//    }
//
//    fun removeFragment() {
//        fragments.removeLast()
//        notifyItemRemoved(fragments.size)
//    }
}