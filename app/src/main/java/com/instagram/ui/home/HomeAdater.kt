package com.instagram.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.databinding.ItemPostBinding
import com.instagram.model.Image
import com.instagram.model.Post
import com.instagram.ui.home.ModalBottomSheet.Companion.TAG
import com.instagram.ui.home.post.ItemPostAdapter
import com.instagram.ui.home.post.ItemPostViewModel


class HomeAdapter(private val lifecycleOwner: LifecycleOwner, private val context: Fragment) :
    ListAdapter<Post, HomeAdapter.HomeViewHolder>(
        HomeDiffUtil()
    ) {
    lateinit var postViewModel: ItemPostViewModel
    lateinit var postAdapter: ItemPostAdapter
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val database = Firebase.database(firebaseUrl).reference
    private val userUid = Firebase.auth.currentUser?.uid

    // onCreateViewHolder = 새 ViewHolder를 만든다. TODO. Udacity 3-12
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    // onBindViewHolder = 만들어진 ViewHolder에 정보를 넣는다.
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            postImage(post.posts)
            binding.post = post
            binding.executePendingBindings()
            setItemMenuButton()
            setButtonHeart()
            if(post.likeUserList.contains(userUid))
                binding.buttonHeart.setImageResource(R.drawable.heart_clicked) // 반대인데 왜 이게 정상으로 나오지??
            else
                binding.buttonHeart.setImageResource(R.drawable.heart) // 반대인데 왜 이게 정상으로 나오지??
        }

        private fun postImage(postImages: List<Image>?) {
            postViewModel = ItemPostViewModel(postImages)
            postAdapter = ItemPostAdapter()
            binding.viewpagerPostImage.adapter = postAdapter
            postViewModel.postImages.observe(this@HomeAdapter.lifecycleOwner) { images ->
                postAdapter.submitList(images)

                // indicator
                TabLayoutMediator(binding.viewpagerPostImageIndicator,
                    binding.viewpagerPostImage) { tab, position ->

                }.attach()
            }
        }

        private fun setItemMenuButton() {
            binding.buttonPostMenu.setOnClickListener {
                binding.post?.postUid?.let { postUid ->
                    ModalBottomSheet(postUid).show(context.parentFragmentManager, TAG)
                }
            }
        }

        private fun setButtonHeart() {
            binding.buttonHeart.setOnClickListener {
                onStarClicked(it)
            }
        }

        private fun onStarClicked(view: View) {
            val postKey = binding.post?.postUid
            val postRef = database.child("posts/$postKey")
            val userRef = database.child("users/$userUid/like_posts")

            postRef.runTransaction(object : Transaction.Handler {
                override fun doTransaction(mutableData: MutableData): Transaction.Result {
                    val postValue =
                        mutableData.getValue(Post::class.java) ?: return Transaction.success(mutableData)
                    // 좋아요 목록에 있다면(=이미 좋아요) 좋아요 취소
                    if (postValue.likeUserList.contains(userUid)) {
                        // Unstar the post and remove self from stars
                        postValue.likeCount = postValue.likeCount - 1
                        postValue.likeUserList.remove(userUid)

                        // users에 좋아요 취소
                        userRef.updateChildren(mapOf(postKey to false))
                    } else {
                        // Star the post and add self to stars
                        postValue.likeCount = postValue.likeCount + 1
                        if (userUid != null) {
                            postValue.likeUserList.add(userUid)
                        }

                        // users에 좋아요
                        userRef.updateChildren(mapOf(postKey to true))
                    }

                    // Set value and report transaction success
                    mutableData.value = postValue
                    return Transaction.success(mutableData)
                }

                override fun onComplete(
                    databaseError: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?,
                ) {
                    // Transaction completed
                }
            })
        }
    }
}

class HomeDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postUid == newItem.postUid
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}