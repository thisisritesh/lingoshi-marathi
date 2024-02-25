package com.riteshmaagadh.lla_base.ui.home.quicklearn.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteshmaagadh.lla_base.data.db.LessonsDatabase
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.modals.QlPhrase
import com.riteshmaagadh.lla_base.data.repositories.AppDataRepository
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuickLearnDetailViewModel : ViewModel() {

    private val repository: AppDataRepository by lazy { AppDataRepository() }


    private val _qlPhrasesLiveData: MutableLiveData<NetworkState<List<QlPhrase>>> =
        MutableLiveData()
    val qlPhrasesLiveData: LiveData<NetworkState<List<QlPhrase>>>
        get() = _qlPhrasesLiveData


    fun getQlPhrases(context: Context, docId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val lessonsDao = LessonsDatabase.invoke(context).lessonsDao()
            val result = repository.getQlPhrases(docId)
            result.collect {
                when (it) {
                    is NetworkState.Success -> {
                        withContext(Dispatchers.Main) {
                            _qlPhrasesLiveData.value = it
                        }
                    }

                    is NetworkState.Failed -> {
                        withContext(Dispatchers.Main) {
                            _qlPhrasesLiveData.value = NetworkState.failed(it.message)
                        }
                    }

                    is NetworkState.Loading -> {
                        withContext(Dispatchers.Main) {
                            _qlPhrasesLiveData.value = NetworkState.loading()
                        }
                    }
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