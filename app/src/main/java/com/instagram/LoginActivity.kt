package com.instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.databinding.ActivityLoginBinding
import com.instagram.ui.SignUpActivity
import com.instagram.ui.SignUpFragment

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInBtn = binding.buttonLogin
        signInBtn.setOnClickListener{
            signIn(binding.etId.text.toString(), binding.etPassword.text.toString())
        }

        val signUpBtn = binding.buttonSignUp
        signUpBtn.setOnClickListener {
            moveSignUpPage()
        }
    }

    private fun signIn(id: String, password: String) {
        auth = Firebase.auth
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
    private fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        else {
            // TODO user가 null일 경우 (근데 NULL일 수가 있나?)

        }
    }

    fun moveSignUpPage() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}