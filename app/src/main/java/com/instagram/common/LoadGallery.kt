//package com.instagram.common
//
//import android.content.Intent
//import android.net.Uri
//import android.provider.MediaStore
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.bumptech.glide.Glide
//
//class LoadGallery {
//    private fun activityResultLauncher(imageView: ImageView): ActivityResultLauncher<Intent> {
//        val launcher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == AppCompatActivity.RESULT_OK) {
//                result.data.let { intent ->
//                    // 1. 사진을 한 장도 선택하지 않았을 때
//                    if (intent == null) {
//                        Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                    // 2. 이미지를 한 장 선택한 경우
//                    else if (intent.clipData == null) {
//                        val imageUri: Uri = intent.data!!
//                        uriList.add(imageUri)
//                    }
//                    // 3. 이미지를 여러 장 선택한 경우
//                    else {
//                        val clipData = intent.clipData
//                        // 3-1. 최대 10장까지만 가능
//                        if (clipData!!.itemCount > 10) {
//                            Toast.makeText(this, "최대 10장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
//                        }
//                        // 3-2. 10장 이하 선택하였을 경우
//                        else {
//                            Toast.makeText(this, "여러장을 선택하였습니당", Toast.LENGTH_SHORT).show()
//                            for (i in 0 until clipData.itemCount) {
//                                val imageUri = clipData.getItemAt(i).uri
//                                uriList.add(imageUri)
//                            }
//                        }
//                    }
//                    Glide.with(this)
//                        .load(uriList[0]) // viewpager로 바꾸면 for문 사용
//                        .into(imageView)
//                }
//            }
//        }
//        return launcher
//    }
//    fun openGallery(launcher: ActivityResultLauncher<Intent>) {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_PICK
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // 다중 이미지를 가져올 수 있도록 세팅
//        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//        launcher.launch(intent)
//    }
//
//}