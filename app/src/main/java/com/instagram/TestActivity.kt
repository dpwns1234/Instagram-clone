package com.instagram

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text
import java.io.IOException
import java.net.URI
import java.net.URL


const val GITHUB_BASE_URL = "https://api.github.com/search/repositories?q=android&sort=stars"
const val PARAM_QUERY = "q"
const val PARAM_SORT = "sort"


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val button = findViewById<Button>(R.id.button_bring)
        val editText = findViewById<EditText>(R.id.et_url)
        val textView = findViewById<TextView>(R.id.tv_bring_url)
        button.setOnClickListener {
            makeUri(editText, textView)
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
    }
}