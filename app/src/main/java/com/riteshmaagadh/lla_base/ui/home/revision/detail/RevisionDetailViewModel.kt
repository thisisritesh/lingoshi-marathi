package com.riteshmaagadh.lla_base.ui.home.revision.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RevisionDetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "RevisionViewModel"
    }


    private val _topicsLiveData: MutableLiveData<NetworkState<List<Topic>>> = MutableLiveData()
    val topicsLiveData: LiveData<NetworkState<List<Topic>>>
        get() = _topicsLiveData

    private val _titleLiveData: MutableLiveData<NetworkState<Lesson.Title>> = MutableLiveData()
    val titleLiveData: LiveData<NetworkState<Lesson.Title>>
        get() = _titleLiveData


    fun getTopics(context: Context, lessonName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val lessonsDao = LessonsDatabase.invoke(context).lessonsDao()
            val sublessons = lessonsDao.getSubLessonsByLessonName(lessonName) as ArrayList<Topic>
            sublessons.removeIf {
                it.layoutType != "info"
            }
            withContext(Dispatchers.Main) {
                _topicsLiveData.value = NetworkState.success(sublessons)
            }
        }
    }

}