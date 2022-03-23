package com.instagram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemPostBinding
import com.instagram.model.Image
import com.instagram.model.Post

class ItemPostAdapter(): ListAdapter<Post, ItemPostAdapter.ItemPostViewHolder>(ItemPostDiffUtil()) {
    class ItemPostViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ItemPostDiffUtil: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postIdx == newItem.postIdx
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}