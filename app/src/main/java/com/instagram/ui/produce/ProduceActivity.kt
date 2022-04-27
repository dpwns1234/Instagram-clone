package com.instagram.ui.produce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.R
import java.io.File
import java.util.Date
import java.text.SimpleDateFormat

class ProduceActivity: AppCompatActivity() {
    private val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produce)

        val button = findViewById<Button>(R.id.button_gallery)
        val imageView = findViewById<ImageView>(R.id.iv_post_image)
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val uri = intent?.data
                Glide.with(this)
                    .load(uri)
                    .into(imageView)

            }
        }

        button.setOnClickListener {
            loadGallery(launcher)
        }
        //uploadToStorage()

    }

    private fun loadGallery(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(intent)
    }


    private fun uploadToStorage() {
        val uri = Uri.fromFile(File("/sdcard/image.jpg"))
        val storageRef = storage.reference
        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        val fileName = sdf.format(Date()) + ".jpg"

        val riverRef = storageRef.child("images/$fileName")
        riverRef.putFile(uri)
            .addOnProgressListener { taskSnapshot ->
                val btf = taskSnapshot.bytesTransferred
                val tbc = taskSnapshot.totalByteCount
                val progress: Double = 100.0 * btf / tbc

                // progressBar.progress = progress.toInt()
            }.addOnFailureListener { Log.d("hihi", "업로드 실패") }
            .addOnSuccessListener { Log.d("hihi", "업로드 성공") }
    }

}