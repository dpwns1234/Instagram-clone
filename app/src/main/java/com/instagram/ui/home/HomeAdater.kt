package com.instagram.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.instagram.databinding.ItemPostBinding
import com.instagram.model.Image
import com.instagram.model.Post
import com.instagram.ui.ModalBottomSheet
import com.instagram.ui.home.post.ItemPostAdapter
import com.instagram.ui.home.post.ItemPostViewModel

class HomeAdapter(private val context: LifecycleOwner): ListAdapter<Post, HomeAdapter.HomeViewHolder>(
    HomeDiffUtil()
) {
    lateinit var postViewModel: ItemPostViewModel
    lateinit var postAdapter: ItemPostAdapter

    // onCreateViewHolder = 새 ViewHolder를 만든다. TODO. Udacity 3-12
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    // onBindViewHolder = 만들어진 ViewHolder에 정보를 넣는다.
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            postImage(post.posts)
            binding.post = post
            binding.executePendingBindings()

        }

        private fun postImage(postImages: List<Image>?) {
            postViewModel = ItemPostViewModel(postImages)
            postAdapter = ItemPostAdapter()
            binding.viewpagerPostImage.adapter = postAdapter
            postViewModel.postImages.observe(this@HomeAdapter.context) { images ->
                postAdapter.submitList(images)

                // indicator
                TabLayoutMediator(binding.viewpagerPostImageIndicator, binding.viewpagerPostImage) { tab, position ->

                }.attach()
            }
        }

    }
}

class HomeDiffUtil: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postUid == newItem.postUid
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}