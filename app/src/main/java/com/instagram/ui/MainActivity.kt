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
        // TODO. Permission Denial: opening provider com.android.providers.media.MediaDocumentsProvider from ProcessRecord{91cf1c1 11539:com.instagram/u0a148} (pid=11539, uid=10148) requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs

    }
}