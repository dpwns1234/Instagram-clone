package com.instagram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    val auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInBtn = findViewById<Button>(R.id.button_login)
        signInBtn.setOnClickListener{
            signIn(findViewById<EditText>(R.id.et_id).toString(), findViewById<EditText>(R.id.et_password).toString())
        }
    }

    private fun signIn(id: String, password: String) {
        auth?.signInWithEmailAndPassword(id, password)
            ?.addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Toast.makeText(baseContext, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                    moveMainPage(auth.currentUser)
                }
                else {
                    Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun moveMainPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}