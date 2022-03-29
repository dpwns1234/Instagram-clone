//package com.instagram
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.instagram.databinding.ItemPostImageBinding
//import com.instagram.model.Image
//
//class ItemPostImageAdapter: ListAdapter<Image, ItemPostImageAdapter.ItemPostImageViewHolder>(ItemPostImageDiffUtil()) {
//    // TODO. viewModel을 binding에 꼭 넣어줘야 하나?? 안 넣으면 동작 안 하려나?
//    class ItemPostImageViewHolder(private val binding: ItemPostImageBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(image: Image) {
//            // TODO. bind 공부! -> bind함수가 발생하는 경우가 스크롤을 내려서 새로운 item이 발견되었을 때인가?
//            binding.image = image
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPostImageViewHolder {
//        val binding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ItemPostImageViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ItemPostImageViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//}
//
//class ItemPostImageDiffUtil: DiffUtil.ItemCallback<Image>() {
//    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
//        return oldItem.imageUrl == newItem.imageUrl
//    }
//    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
//        return oldItem == newItem
//    }
//}