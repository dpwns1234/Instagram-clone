package com.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.ActivityLoginBinding
import com.instagram.model.User
import com.instagram.ui.MainActivity
import com.instagram.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInBtn = binding.buttonLogin
        signInBtn.setOnClickListener{
            //createUser()
            signIn(binding.etId.text.toString(), binding.etPassword.text.toString())
        }

        val signUpBtn = binding.buttonSignUp
        signUpBtn.setOnClickListener {
            moveSignUpPage()
        }


    }

//    override fun onStart() {
//        super.onStart()
//        moveMainPage(auth?.currentUser)
//    }

    private fun signIn(id: String, password: String) {
        auth = Firebase.auth
        FirebaseAuth.getInstance()
        if(id.isNotEmpty() && password.isNotEmpty()) {
            auth?.signInWithEmailAndPassword(id, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                        moveMainPage(auth?.currentUser)
                    } else {
                        Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    // 자동 로그인 - 로그아웃 구현 전까지 주석
    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if(user != null) {
            moveMainPage(user)
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        else {
            // TODO user가 null일 경우 (근데 NULL일 수가 있나?)
        }
    }

    private fun moveSignUpPage() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

}