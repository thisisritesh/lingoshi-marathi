package com.riteshmaagadh.lla_base.domain.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.utils.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserDataUseCase(private val context: Context) {


    suspend fun getUserData(): LiveData<UserData> {
        val job: Deferred<LiveData<UserData>> = CoroutineScope(Dispatchers.IO).async {
            val userLiveData = LessonsDatabase.invoke(context).lessonsDao().getUserData(AppConstants.DEFAULT_USER_ID)
            userLiveData
        }
        return job.await()
    }

    suspend fun saveUserData(userData: UserData) {
        CoroutineScope(Dispatchers.IO).launch {
            LessonsDatabase.invoke(context).lessonsDao().saveUserData(userData)
        }
    }

}