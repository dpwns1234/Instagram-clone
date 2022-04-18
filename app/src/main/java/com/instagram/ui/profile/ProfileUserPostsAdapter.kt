package com.instagram.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemProfileImagesBinding
import com.instagram.databinding.ItemProfileUserImagesBinding
import com.instagram.model.PreviewPost

class ProfileUserPostsAdapter:ListAdapter<PreviewPost, ProfileUserPostsAdapter.ProfileUserPostViewHolder>(ProfileUserPostDiffUtil()) {

    lateinit var binding: ItemProfileUserImagesBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileUserPostViewHolder {
        binding = ItemProfileUserImagesBinding.inflate(LayoutInflater.from(parent.context))
        return ProfileUserPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileUserPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileUserPostViewHolder(binding: ItemProfileUserImagesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(previewPost: PreviewPost) {
            binding.previewPost = previewPost
            binding.executePendingBindings()
        }
    }
}

class ProfileUserPostDiffUtil: DiffUtil.ItemCallback<PreviewPost>() {
    override fun areItemsTheSame(oldItem: PreviewPost, newItem: PreviewPost): Boolean {
        return oldItem.postIdx == newItem.postIdx
    }

    override fun areContentsTheSame(oldItem: PreviewPost, newItem: PreviewPost): Boolean {
        return oldItem == newItem
    }

}