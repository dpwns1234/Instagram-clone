package com.instagram.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.databinding.ModalBottomSheetContentBinding

class ModalBottomSheet(private val postUid: String): BottomSheetDialogFragment() {
    private lateinit var binding: ModalBottomSheetContentBinding
    private val firebaseUrl =
        "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = "이 게시물을 삭제하시겠어요?"
        val message = "삭제하시면 다시 복구하실 수 없습니다. 그래도 삭제하시겠습니까?"
        val negative = "삭제 안함"
        val positive = "삭제"
        binding.tvDeletePost.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(negative) { dialog, which ->
                    // Respond to negative button press
                    dialog.dismiss()
                }
                .setPositiveButton(positive) { dialog, which ->
                    // Respond to positive button press
                    Toast.makeText(requireContext(), "데이터베이스에서 삭제", Toast.LENGTH_SHORT).show()
                    acceptDelete()
                }
                .show()
        }

    }

    private fun acceptDelete() {
        val userUid = Firebase.auth.uid
        val postKey = postUid
        databaseRef.child("posts").get()
        databaseRef.updateChildren(hashMapOf<String, Any?>(
            "users/$userUid/profiles/posts/$postKey" to null,
            "posts/$postKey" to null
        ))
    }
}