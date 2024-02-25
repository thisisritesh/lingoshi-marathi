package com.riteshmaagadh.lla_base.ui.home.quicklearn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.repositories.AppDataRepository
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuickLearnViewModel : ViewModel() {

    private val repository: AppDataRepository by lazy { AppDataRepository() }


    private val _quickLearnLiveData: MutableLiveData<NetworkState<List<QuickLearn>>> = MutableLiveData()
    val quickLearnLiveData: LiveData<NetworkState<List<QuickLearn>>>
        get() = _quickLearnLiveData


    fun getQuickLearn(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val lessonsDao = LessonsDatabase.invoke(context).lessonsDao()
            val quickLearnList = lessonsDao.getQuickLearn()
            if (quickLearnList.isEmpty()) {
                val result = repository.getQuickLearnData()
                result.collect {
                    if (it is NetworkState.Success) {
                        lessonsDao.saveQuickLearn(it.data)
                    }
                    withContext(Dispatchers.Main) {
                        _quickLearnLiveData.value = it
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _quickLearnLiveData.value = NetworkState.success(quickLearnList)
                }
            }
        }
    }


//    fun getUserData(context: Context) {
//        _userLiveData.value = NetworkState.loading()
//        viewModelScope.launch(Dispatchers.Main) {
//            val userDataRepo = UserDataRepo(context)
//            val userData = userDataRepo.getUserData()
//
//            _userLiveData.value = NetworkState.success(userData)
//        }
//    }



}