package com.instagram.repository.profile

import com.instagram.model.PreviewPost
import com.instagram.model.Profile
import retrofit2.Response

class ProfileRepository(private val profileRemoteDataSource: ProfileRemoteDataSource) {
    // 항상 코루틴 스콥에서 실행하도록 강제하는 방법 : suspend 사용
    suspend fun getProfileData(userUid: String): Profile {
//        withContext를 통해 코루틴이 실행될 스레드를 변경할 수 있다.
//        withContext(Dispatchers.IO) {
//            profileRemoteDataSource.getProfileData()
//        }

        // 하지만 retrofit 라이브러리에서 withContext 역할을 해주기 때문에 굳이 해줄 필요는 없다.
        return profileRemoteDataSource.getProfileData(userUid)
    }

    suspend fun getPosts(userUid: String): List<PreviewPost> {
        return profileRemoteDataSource.getPreviewPosts(userUid)
    }

    suspend fun getFollowingList(userUid: String) : Response<MutableList<String>> {
        return profileRemoteDataSource.getFollowingList(userUid)
    }
}