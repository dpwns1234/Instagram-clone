package com.instagram.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.databinding.FragmentSignUpDetailBinding

class SignUpDetailFragment : Fragment() {
    private lateinit var binding: FragmentSignUpDetailBinding
    private var auth: FirebaseAuth? = null

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
                        Toast.makeText(requireContext(), "계정 생성 완료", Toast.LENGTH_SHORT).show()
                        activity?.finish()
                    }
                    else {
                        Toast.makeText(requireContext(), "계정 생성 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}