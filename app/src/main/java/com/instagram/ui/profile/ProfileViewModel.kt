package com.instagram.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.instagram.AssetLoader
import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import retrofit2.Retrofit
import kotlin.concurrent.thread

class ProfileViewModel(private val context: Context): ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    var profile: LiveData<Profile> = _profile

    private val _profilePosts = MutableLiveData<List<PreviewPost>>()
    var profilePosts: LiveData<List<PreviewPost>> = _profilePosts

    private val _profileUserPosts = MutableLiveData<List<PreviewPost>>()
    var profileUserPosts: LiveData<List<PreviewPost>> = _profileUserPosts

    private val firebaseUrl = "https://instagram-android-65931-default-rtdb.asia-southeast1.firebasedatabase.app/"

    init{
        loadProfileFromFirebase()
        loadProfile()
    }

    private fun loadProfile() {
        val gson = Gson()

        val assetLoader = AssetLoader(context)
        val jsonData = assetLoader.getJsonString("profile.json")
        val profileData = gson.fromJson(jsonData, Profile::class.java)
        //_profile.value = profileData
        _profilePosts.value = profileData.posts!!           // TODO. 이것처럼 home도 list는 데이터 따로 만들어서 할당하는건 어떰??
        _profileUserPosts.value = profileData.userPosts!!   // TODO. 근데 생각해보니까 데이터 할당하는건 따로 할 필요는 없지 않나?? 데이터를 따로 만드는건 괜찮아도.
    }

    // TODO. 마지막. posts, userPosts database로 load하기
    private fun loadProfileFromFirebase() {
        val auth = Firebase.auth
        val database = Firebase.database(firebaseUrl).reference
            .child("users")
            .child("profiles")
            .child("QsMOoLiVIFX31zNtUWzK1FhCfHh1")
            //.child(auth.currentUser!!.uid)

        val profileListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val profile = snapshot.getValue<Profile>()
                if (profile == null) {
                    // TODO. 해당 위치에 저장된 데이터가 없음을 뜻함.
                } else {
                    _profile.postValue(profile!!)
                    //_profilePosts.value = profile.posts!!
                    //_profileUserPosts.value = profile.userPosts!!
                }
                // TODO. posts, userPosts 나오도록 수정

            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("ProfileViewModel", "loadPost:onCancelled")
            }
        }
        database.addValueEventListener(profileListener)
    }
}