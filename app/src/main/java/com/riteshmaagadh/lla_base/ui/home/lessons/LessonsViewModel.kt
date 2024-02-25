package com.riteshmaagadh.lla_base.ui.home.lessons

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.repositories.AppDataRepository
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LessonsViewModel : ViewModel() {

    private val repository: AppDataRepository by lazy { AppDataRepository() }


    private val _lessonsLiveData: MutableLiveData<NetworkState<List<Lesson>>> = MutableLiveData()
    val lessonsLiveData: LiveData<NetworkState<List<Lesson>>>
        get() = _lessonsLiveData


    fun getLessons(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val lessonsDao = LessonsDatabase.invoke(context).lessonsDao()
            val lessons = lessonsDao.getLessons()
            if (lessons.isEmpty()) {
                val result = repository.getLessons()
                if (result is NetworkState.Success) {
                    lessonsDao.saveLessonsData(result.data)
                }
                withContext(Dispatchers.Main) {
                    _lessonsLiveData.value = result
                }
            } else {
                withContext(Dispatchers.Main) {
                    _lessonsLiveData.value = NetworkState.success(lessons)
                }
            }
        }
    }


}