//package com.instagram.ui
//
//import android.content.ContentValues
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.instagram.R
//
//
//class ProducePostFragment : Fragment() {

//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_produce_post, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//    }
//
//    companion object {
//        private const val IMG_MIME_TYPE = "image/*"
//    }
//
//    private var mCameraPhotoPath: Uri? = null
//
//    private fun imageChooser(requestCode: Int) {
//        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//            resolveActivity(packageManager)?.run {
//                createImageFile()?.let {
//                    if(fileUri != null) {
//                        putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
//                    }
//                }
//            }
//        }
//
//        val selectionIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            type = IMG_MIME_TYPE
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        }
//        val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
//            putExtra(Intent.EXTRA_INTENT, selectionIntent)
//            putExtra(Intent.EXTRA_TITLE, "Choose action type")
//            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pictureIntent))
//        }
//        // TODO. 이거 사용 못한다는데 상관 없나?
////        startActivityForResult(chooserIntent, requestCode)
//        startActivity(chooserIntent)
//    }
//
//    private fun createImageFile(): Uri? {
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.MIME_TYPE, IMG_MIME_TYPE)
//        }
//        val resolver = applicationContext.contentResolver
//        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//            .also { uri ->
//                mCameraPhotoPath = uri
//            }
//    }
//}