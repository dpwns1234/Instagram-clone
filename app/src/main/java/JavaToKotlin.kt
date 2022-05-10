//import android.content.Intent
//import android.graphics.Bitmap
//import android.provider.MediaStore
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.FirebaseStorage
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import kotlin.Throws
//
//class JavaToKotlin {
//    fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
//        if (imageReturnedIntent == null
//            || imageReturnedIntent.data == null
//        ) {
//            return
//        }
//
//        // aiming for ~500kb max. assumes typical device image size is around 2megs
//        val scaleDivider = 4
//        try {
//
//            // 1. Convert uri to bitmap
//            val imageUri = imageReturnedIntent.data
//            val fullBitmap =
//                MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri)
//
//            // 2. Get the downsized image content as a byte[]
//            val scaleWidth = fullBitmap.width / scaleDivider
//            val scaleHeight = fullBitmap.height / scaleDivider
//            val downsizedImageBytes = getDownsizedImageBytes(fullBitmap, scaleWidth, scaleHeight)
//
//            // 3. Upload the byte[]; Eg, if you are using Firebase
//            val storageReference = FirebaseStorage.getInstance().getReference("/somepath")
//            storageReference.putBytes(downsizedImageBytes)
//        } catch (ioEx: IOException) {
//            ioEx.printStackTrace()
//        }
//    }
//
//    @Throws(IOException::class)
//    fun getDownsizedImageBytes(
//        fullBitmap: Bitmap?,
//        scaleWidth: Int,
//        scaleHeight: Int
//    ): ByteArray {
//        val scaledBitmap =
//            Bitmap.createScaledBitmap(fullBitmap!!, scaleWidth, scaleHeight, true)
//
//        // 2. Instantiate the downsized image content as a byte[]
//        val baos = ByteArrayOutputStream()
//        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        return baos.toByteArray()
//    }
//}