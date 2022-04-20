package com.instagram.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemFeedBinding
import com.instagram.model.Feed

class FeedAdapter: ListAdapter<Feed, FeedAdapter.FeedViewHolder>(FeedDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemFeedBinding.inflate(LayoutInflater.from(parent.context))
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FeedViewHolder(private val binding: ItemFeedBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: Feed) {
            binding.feed = feed
            binding.executePendingBindings()
        }
    }
}

class FeedDiffUtil: DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.user.nickname == newItem.user.nickname
    }
    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}