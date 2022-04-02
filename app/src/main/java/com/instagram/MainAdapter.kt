package com.instagram

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemPostBinding
import com.instagram.databinding.ItemPostImageBinding
import com.instagram.model.Feed
import com.instagram.model.Image
import com.instagram.model.Post

class MainAdapter(private val context: AppCompatActivity): ListAdapter<Post, MainAdapter.MainViewHolder>(MainDiffUtil()) {
    lateinit var postViewModel: ItemPostViewModel
    val postAdapter = ItemPostAdapter()

    // onCreateViewHolder = 새 ViewHolder를 만든다. TODO. Udacity 3-12
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    // onBindViewHolder = 만들어진 ViewHolder에 정보를 넣는다.
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
            postImage(post.postImages)
        }

        fun postImage(postImages: List<Image>) {
            postViewModel = ItemPostViewModel(postImages)

            postViewModel.postImages.observe(context) { images ->
                postAdapter.submitList(images)
            }
        }

    }
}

class MainDiffUtil: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postIdx == newItem.postIdx
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}