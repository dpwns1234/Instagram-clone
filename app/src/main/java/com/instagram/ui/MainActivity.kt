package com.instagram.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.instagram.R
import com.instagram.ui.login.LoginActivity

class MainActivity:AppCompatActivity() {
    private val KITKAT_VALUE = 1002
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        // TODO. 이거 강의 다시 보기 ( 뭐하는 건지 )
        val navController =
            supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let {
            bottomNavigationView.setupWithNavController(it)
        }

        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent();
            intent.action = Intent.ACTION_GET_CONTENT;
            intent.type = "*/*";
            startActivityForResult(intent, KITKAT_VALUE);
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.type = "*/*";
            startActivityForResult(intent, KITKAT_VALUE);
        }
    }
}