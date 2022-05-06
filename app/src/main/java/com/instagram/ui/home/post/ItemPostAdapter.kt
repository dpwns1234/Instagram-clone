package com.instagram.ui.home.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instagram.databinding.ItemPostImageBinding
import com.instagram.model.Image
import com.instagram.ui.ModalBottomSheet

class ItemPostAdapter: ListAdapter<Image, ItemPostAdapter.ItemPostViewHolder>(ItemPostImageDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostViewHolder {
        val binding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemPostViewHolder(private val binding: ItemPostImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            // TODO. bind함수가 발생하는 경우가 스크롤을 내려서 새로운 item이 발견되었을 때인가? -> 아마 그럴걸?
            binding.image = image
        }
    }
}

class ItemPostImageDiffUtil: DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        // TODO. 나중에 Image에 idx 만들어서 수정하기
        return oldItem.imageIdx == newItem.imageIdx
    }
    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}