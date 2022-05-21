package com.instagram.ui.comment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.ActivityCommentBinding
import com.instagram.model.Comment
import com.instagram.model.Post
import com.instagram.model.User
import com.instagram.ui.profile.ProfileViewModel

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val database = Firebase.database(firebaseUrl)
    private val userUid = Firebase.auth.currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postUid = intent.getStringExtra("postUid")
        val viewModel = CommentViewModel(postUid!!, userUid)

        setCommentAdapter(viewModel)
        setBasicData(viewModel)
        setButtonShowComment(postUid, viewModel)
        setButtonBack()
    }

    private fun setBasicData(viewModel: CommentViewModel) {
        viewModel.post.observe(this) { post ->
            binding.post = post
        }
        viewModel.user.observe(this) { user ->
            binding.user = user
        }
    }

    private fun setCommentAdapter(viewModel: CommentViewModel) {
        val commentAdapter = CommentAdapter()
        viewModel.comments.observe(this) { comments ->
            commentAdapter.submitList(comments)
        }

        binding.rvComments.adapter = commentAdapter
        binding.lifecycleOwner = this
    }

    private fun setButtonShowComment(
        postUid: String?,
        viewModel: CommentViewModel,
    ) {
        binding.tvWriteComment.setOnClickListener {
            val databaseRef = database.getReference("posts/$postUid")
            val commentKey = databaseRef.push().key!!
            val comment = binding.etWriteComment.text.toString()
            val createdAt = System.currentTimeMillis()
            val commentValue = Comment(commentKey, viewModel.user.value!!, comment, createdAt)

            databaseRef.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val postValue = currentData.getValue(Post::class.java)
                        ?: return Transaction.success(currentData)
                    postValue.comments.add(commentValue)

                    currentData.value = postValue
                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?,
                ) {
                    // 키보드 내리기
                    binding.etWriteComment.text = null
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.etWriteComment.windowToken,
                        0)
                }
            })
        }
    }

    private fun setButtonBack() {
        binding.buttonBack.setOnClickListener {
            this.finish()
        }
    }
}