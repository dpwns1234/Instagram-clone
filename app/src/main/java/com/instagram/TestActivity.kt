package com.instagram

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.instagram.model.Image
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URI
import java.net.URL



class TestActivity : AppCompatActivity() {
    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"
    private val databaseRef = Firebase.database(firebaseUrl).reference
    private val storage = Firebase.storage.reference
    private val user = Firebase.auth.currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val storageImage = findViewById<ImageView>(R.id.iv_storage)
        val databaseImage = findViewById<ImageView>(R.id.iv_database)

        //storageImage.setImageURI(storage.getFile())
        databaseRef.child("posts/${user.uid}/post_images").get().addOnSuccessListener { snapshot ->
            val images = snapshot.getValue<List<Image>>()
            if (images != null) {
                databaseImage.setImageURI(Uri.parse(images[0].imageUrl))
                Log.d("hihi", "uri: ${Uri.parse(images[0].imageUrl)}, url: ${images[0].imageUrl}")
            }
        }


    }

    private fun makeUri(editText: EditText, textView: TextView) {
        val getEditTextString = editText.text.toString()

        // val builtUril = Uri.parse(GITHUB_BASE_URL) 아마 이거로 해도 될 듯
        val builtUri = Uri.parse(getEditTextString).buildUpon()
            .build()

        try {
            val url = URL(builtUri.toString())
            val githubSearchResults = NetworkUtils().getResponseFromHttpUrl(url)
            textView.text = githubSearchResults
        } catch(e: IOException) {
            e.stackTrace
        }

    }
}

class NetworkUtils {

    fun getResponseFromHttpUrl(url: URL): String {
        return "hi"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(Retrofit::class.java)
    }
}