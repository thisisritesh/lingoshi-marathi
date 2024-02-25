package com.riteshmaagadh.lla_base.ui.home.revision.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.databinding.ActivityRevisionDetailBinding
import com.riteshmaagadh.lla_base.ui.learning.LearnedTopicsAdapter
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.NetworkState
import com.riteshmaagadh.lla_base.utils.Pref
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener

class RevisionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRevisionDetailBinding
    private lateinit var revisionDetailViewModel: RevisionDetailViewModel
    private lateinit var adapter: LearnedTopicsAdapter
    private val topics: MutableList<Topic> = mutableListOf()

    companion object {
        const val LESSON_NAME = "lesson_name"
        const val SCREEN_HEADING_TEXT = "screen_heading_txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityRevisionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView13.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                finish()
            }
        })

        val lessonName = intent.extras?.getString(LESSON_NAME)
        val headingTxt = intent.extras?.getString(SCREEN_HEADING_TEXT)
        binding.screenHeadingTv.text = headingTxt


        val langCode = Pref(this).getPrefString(AppConstants.PREFERRED_LANG)

        adapter = LearnedTopicsAdapter(
            topics,
            langCode, this
        )
        binding.topicsRv.adapter = adapter

        revisionDetailViewModel = ViewModelProvider(this)[RevisionDetailViewModel::class.java]

        revisionDetailViewModel.topicsLiveData.observe(this) {
            when (it) {
                is NetworkState.Loading -> {
//                    Log.e(LearningActivity.TAG, "Loading: ")
                }

                is NetworkState.Failed -> {
//                    Log.e(LearningActivity.TAG, "Failed: " + it.message)
                }

                is NetworkState.Success -> {
                    topics.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }


//        revisionDetailViewModel.titleLiveData.observe(this) {
//            when (it) {
//                is NetworkState.Loading -> {
////                    Log.e(LearningActivity.TAG, "Loading: ")
//                }
//
//                is NetworkState.Failed -> {
////                    Log.e(LearningActivity.TAG, "Failed: " + it.message)
//                }
//
//                is NetworkState.Success -> {
//                    binding.screenHeadingTv.text = it.data.getTitle(langCode)
//                }
//            }
//        }

//        revisionDetailViewModel.getTitle(this, lessonName!!)
        revisionDetailViewModel.getTopics(this, lessonName!!)

    }


}