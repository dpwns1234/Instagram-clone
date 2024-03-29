package com.instagram.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.FragmentSignUpDetailBinding
import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import com.instagram.ui.MainActivity

class SignUpDetailFragment : Fragment() {
    private lateinit var binding: FragmentSignUpDetailBinding
    private var auth: FirebaseAuth? = null
    private lateinit var database: DatabaseReference
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        // TODO. firebase 회원가입 기본 조건이 있나?? 비밀번호 개수 제한이나 찾아보기

        binding.buttonSignUp.setOnClickListener {
            signUp(binding.etId.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun signUp(id: String, password: String) {
        if(id.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(id, password)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    if(task.isSuccessful) {
                        // Profile 설정
                        initUserProfile(auth!!.uid!!)
                    }
                    else {
                        Toast.makeText(context, "계정 생성 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initUserProfile(uid: String) {
        database = Firebase.database(firebaseUrl).reference
        val profile = Profile(uid, "-", profileImage = "",
            0, 0, 0,
            "-")
        val profileValue = profile.toMap()

        // [POST] users/{uid}/profiles/
        try {
            database.child("users").child(uid).child("profiles").setValue(profileValue)
                .addOnSuccessListener {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
                // TODO. 문제: 이렇게 구성하면 계정은 만들고 프로필 정보 입력하는데 실패하면 중복 계정 문제가 생기니까 나중에 수정
                .addOnFailureListener {
                    Log.w("SignUpDetailFragment", "Failed add user's account")
                }
        } catch (e: Exception) {
            e.stackTrace
        }

    }
}