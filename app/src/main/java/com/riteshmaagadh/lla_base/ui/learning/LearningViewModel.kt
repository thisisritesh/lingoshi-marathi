package com.riteshmaagadh.lla_base.ui.learning

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.data.repositories.AppDataRepository
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearningViewModel : ViewModel() {

    companion object {
        private const val TAG = "LearningViewModel"
    }

    private val repository: AppDataRepository by lazy { AppDataRepository() }


    private val _topicsLiveData: MutableLiveData<NetworkState<List<Topic>>> = MutableLiveData()
    val topicsLiveData: LiveData<NetworkState<List<Topic>>>
        get() = _topicsLiveData



    fun getLessons(context: Context, subLessonId: String? = null, lessonName: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val lessonsDao = LessonsDatabase.invoke(context).lessonsDao()
            if (subLessonId != null) {
                val sublessons = lessonsDao.getSubLessons(subLessonId)
                if (sublessons.isEmpty()) {
                    val result = repository.getTopics(subLessonId)
                    if (result is NetworkState.Success) {
                        lessonsDao.saveSubLessons(result.data)
                    }
                    withContext(Dispatchers.Main) {
                        _topicsLiveData.value = result
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _topicsLiveData.value = NetworkState.success(sublessons)
                    }
                }
            } else if (lessonName != null) {
                val sublessons = lessonsDao.getSubLessonsByLessonName(lessonName)
                withContext(Dispatchers.Main) {
                    _topicsLiveData.value = NetworkState.success(sublessons)
                }
            }
        }
    }


}