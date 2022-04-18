package com.instagram.ui.profile

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemProfileImagesBinding
import com.instagram.model.PreviewPost

class ProfilePostsAdapter: ListAdapter<PreviewPost, ProfilePostsAdapter.ProfilePostViewHolder>(ProfilePostDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {
        val binding = ItemProfileImagesBinding.inflate(LayoutInflater.from(parent.context))
        return ProfilePostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilePostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfilePostViewHolder(private val binding: ItemProfileImagesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(previewPost: PreviewPost) {
            Log.d("play?", previewPost.postImage)
            binding.previewPost = previewPost
            binding.executePendingBindings()
        }
    }
}

class ProfilePostDiffUtil: DiffUtil.ItemCallback<PreviewPost>() {
    override fun areItemsTheSame(oldItem: PreviewPost, newItem: PreviewPost): Boolean {
        return oldItem.postIdx == newItem.postIdx
    }

    override fun areContentsTheSame(oldItem: PreviewPost, newItem: PreviewPost): Boolean {
        return oldItem == newItem
    }

}