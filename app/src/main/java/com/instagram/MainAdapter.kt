package com.instagram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemPostBinding
import com.instagram.databinding.ItemPostImageBinding
import com.instagram.model.Feed
import com.instagram.model.Post

class MainAdapter(private val viewModel: MainViewModel): ListAdapter<Post, MainAdapter.MainViewHolder>(MainDiffUtil()) {
    private lateinit var postImageBinding: ItemPostImageBinding

    // onCreateViewHolder = 새 ViewHolder를 만든다. TODO. Udacity 3-12
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        postImageBinding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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