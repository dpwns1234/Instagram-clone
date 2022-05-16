package com.instagram.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.R
import com.instagram.databinding.ItemSearchHistoryBinding
import com.instagram.model.Profile

class SearchAdapter: ListAdapter<Profile, SearchAdapter.SearchViewHolder>(SearchDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemClicked()
    }

    inner class SearchViewHolder(private val binding: ItemSearchHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.profile = profile
            binding.executePendingBindings()
        }

        fun itemClicked() {
            binding.root.setOnClickListener {
                val action = R.id.action_search_to_user_profile
                it.findNavController().navigate(action, bundleOf("userUid" to binding.profile!!.userUid))
            }
        }
    }
}

class SearchDiffUtil: DiffUtil.ItemCallback<Profile>() {
    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.userUid == newItem.userUid
    }
    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem == newItem
    }
}