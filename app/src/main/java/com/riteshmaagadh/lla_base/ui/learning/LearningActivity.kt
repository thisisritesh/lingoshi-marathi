package com.riteshmaagadh.lla_base.ui.learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.databinding.ActivityLearningBinding
import com.riteshmaagadh.lla_base.databinding.LearnedTopicsBottomSheetBinding
import com.riteshmaagadh.lla_base.databinding.RightAnsItemBinding
import com.riteshmaagadh.lla_base.databinding.WrongAnsItemBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.afterlearn.AfterLearnActivity
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppConstants.DEFAULT_USER_ID
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.DialogCallbacks
import com.riteshmaagadh.lla_base.utils.NetworkState
import com.riteshmaagadh.lla_base.utils.Pref
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.disableButton
import com.riteshmaagadh.lla_base.utils.enableButton
import com.riteshmaagadh.lla_base.utils.playRightAnswerSound
import com.riteshmaagadh.lla_base.utils.playWrongAnswerSound
import com.riteshmaagadh.lla_base.utils.setClickListener
import kotlinx.coroutines.launch


class LearningActivity : AppCompatActivity(), LearningAdapter.AdapterCallbacks {

    private lateinit var binding: ActivityLearningBinding
//    private var placeholderAudio =
//        "https://firebasestorage.googleapis.com/v0/b/marathi-learning-app-acb5e.appspot.com/o/audio_2023-10-18_13-28-53.ogg?alt=media&token=03c4ad28-69c3-4e20-bc2b-64a1d956b528&_gl=1*1sniarz*_ga*MTk2NjIwNjI3OC4xNjc0NDY2NTM4*_ga_CW55HF8NVT*MTY5NzYxNTk1MS44Ny4xLjE2OTc2MTU5ODAuMzEuMC4w"
//    private val placeholderImage =
//        "https://firebasestorage.googleapis.com/v0/b/marathi-learning-app-acb5e.appspot.com/o/19e.png?alt=media&token=8d8fed66-be59-4245-b2d0-c7704ed0da1f&_gl=1*meusvn*_ga*MTk2NjIwNjI3OC4xNjc0NDY2NTM4*_ga_CW55HF8NVT*MTY5NjU3Nzc4NC44My4xLjE2OTY1Nzk0NDkuNDQuMC4w"

    private var rightAnswerCodeTranslation = ""
    private var answerCodeTranslation = ""
    private var rightAnswerCodeOptions = 0
    private var answerCodeOptions = 0

    private lateinit var learningViewModel: LearningViewModel
    private lateinit var topic: Topic
    private var isLastPage = false
    private val learnedTopics: MutableList<Topic> = mutableListOf()
    private val topics: MutableList<Topic> = mutableListOf()
    private var lessonCount: Int = 0
    private lateinit var lessonId: String
    private lateinit var nextLessonId: String
    private lateinit var pref: Pref
    private lateinit var lessonType: String
    private lateinit var adapter: LearningAdapter
    private var lessonTitle: Lesson.Title? = null
    private var subLessonId: String? = null

    private var mUserData: UserData? = null
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(this) }
    private val langCode: String by lazy { Pref(this).getPrefString(AppConstants.PREFERRED_LANG) }

    private val complimentList: List<String> by lazy { listOf(
        getString(R.string.correct_1),
        getString(R.string.correct_2),
        getString(R.string.correct_3),
        getString(R.string.correct_4),
        getString(R.string.correct_5),
        getString(R.string.correct_6)
    ) }

    var totalQuiz = 0
    var wrongQuiz = 0

    var previousTopic: Topic? = null

    companion object {
        const val SUB_LESSONS_COUNT = "sub_lessons_count"
        const val SUB_LESSON_ID = "sub_lesson_id"
        const val LESSON_ID = "lesson_id"
        const val NEXT_LESSON_ID = "next_lesson_id"
        const val LESSON_NAME = "lesson_name"
        private const val TAG = "LearningActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        learningViewModel = ViewModelProvider(this)[LearningViewModel::class.java]

        lifecycleScope.launch {
            userDataUseCase.getUserData().observe(this@LearningActivity) {
                mUserData = it
            }
        }

        pref = Pref(this)

        lessonCount = intent.extras?.getInt(SUB_LESSONS_COUNT)!!
        lessonId = intent.extras?.getString(LESSON_ID)!!

        if (intent.hasExtra(SUB_LESSON_ID)) {
            subLessonId = intent.extras?.getString(SUB_LESSON_ID)!!
        }

        if (intent.hasExtra(LESSON_NAME)) {
            val titleStr = intent.extras?.getString(LESSON_NAME)
            val gson = Gson()
            lessonTitle = gson.fromJson(titleStr, Lesson.Title::class.java)
        }

        if (intent.hasExtra(NEXT_LESSON_ID)) {
            nextLessonId = intent.extras?.getString(NEXT_LESSON_ID)!!
        }


        learningViewModel.topicsLiveData.observe(this) {
            when (it) {
                is NetworkState.Loading -> {
                    Log.e(TAG, "Loading: ")
                }

                is NetworkState.Failed -> {
                    Log.e(TAG, "Failed: " + it.message)
                }

                is NetworkState.Success -> {
                    Log.e(TAG, "Success: " + it.data.size)
                    if (subLessonId == null) {
                        val topicList = it.data as ArrayList<Topic>
                        topicList.removeIf { p0 -> p0.layoutType == "intro" || p0.layoutType == "info" }
                        topics.addAll(topicList)
                    } else {
                        topics.addAll(it.data)
                    }
                    binding.progressBar.max = topics.size
                    adapter.notifyDataSetChanged()
                }
            }
        }

        learningViewModel.getLessons(this, subLessonId, lessonTitle?.en)


        adapter = LearningAdapter(topics, langCode, this, this)
        binding.learningViewPager.adapter = adapter

        binding.learningViewPager.isUserInputEnabled = false
        binding.learningViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                binding.progressBar.progress = position

                isLastPage = position == topics.size - 1
                topic = topics[position]
                binding.nextBtn.text = getString(R.string.continue_)

                if (topic.layoutType == "translation") {
                    previousTopic = null
                    totalQuiz += 1
                    binding.nextBtn.disableButton()
                    return
                }

                if (topic.layoutType == "options") {
                    previousTopic = null
                    totalQuiz += 1
                    binding.nextBtn.disableButton()
                    return
                }

                binding.nextBtn.enableButton()
            }
        })


        binding.nextBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                when (topic.layoutType) {
                    "intro" -> {
                        proceedToNext()
                    }

                    "info" -> {
                        learnedTopics.add(topic)
                        proceedToNext()
                    }

                    "options" -> {
                        if (rightAnswerCodeOptions == answerCodeOptions) {
                            showRightAnsBottomSheet()
                        } else {
                            if (previousTopic == null) {
                                wrongQuiz += 1
                                previousTopic = topic
                            }
                            topic.options?.options?.forEach {
                                if (it.code == topic.options?.rightCode) {
                                    showWrongAnsBottomSheet(it.getDisplayText(langCode))
                                }
                            }
                        }
                    }

                    "translation" -> {
                        if (rightAnswerCodeTranslation == answerCodeTranslation) {
                            showRightAnsBottomSheet()
                        } else {
                            showWrongAnsBottomSheet(getRightAnsFromTranslation(topic.translation!!))
                        }
                    }
                }
            }
        })

        binding.revisionBookIv.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                showLearntItemsBottomSheet()
            }
        })

        binding.dotIndicator.setOnClickListener {
            showLearntItemsBottomSheet()
        }

        binding.closeBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                AppUtils.showQuitLearningDialog(this@LearningActivity, object : DialogCallbacks {
                    override fun onPositiveButtonClicked() {
                        finish()
                    }
                })
            }
        })

    }


    private fun getRightAnsFromTranslation(translation: Topic.Translation): String {
        val charArray = translation.rightCode.toCharArray()
        val stringBuilder = StringBuilder()

        charArray.forEach { char ->
            translation.options?.forEach {
                if (it.code.toInt() == char.digitToInt()) {
                    stringBuilder.append(it.displayText + " ")
                }
            }
        }
        return stringBuilder.toString()
    }


    private fun showLearntItemsBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val learnedTopicsBottomSheetBinding =
            LearnedTopicsBottomSheetBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(learnedTopicsBottomSheetBinding.root)
        val adapter = LearnedTopicsAdapter(learnedTopics, langCode, this)
        learnedTopicsBottomSheetBinding.topicsRv.adapter = adapter
        dialog.show()
    }


    private fun showWrongAnsBottomSheet(rightAnswer: String) {
        playWrongAnswerSound(this)
        val dialog = BottomSheetDialog(this)
        val wrongAnsBinding = WrongAnsItemBinding.inflate(dialog.layoutInflater)
        wrongAnsBinding.correctAnsTv.text = rightAnswer
        dialog.setContentView(wrongAnsBinding.root)
        dialog.setCancelable(false)
        wrongAnsBinding.nextBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    private fun showRightAnsBottomSheet() {
        playRightAnswerSound(this)
        val dialog = BottomSheetDialog(this)
        val rightAnsBinding = RightAnsItemBinding.inflate(dialog.layoutInflater)
        rightAnsBinding.textView3.text = complimentList.random()
        dialog.setContentView(rightAnsBinding.root)
        dialog.setCancelable(false)
        rightAnsBinding.nextBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                proceedToNext()
                dialog.dismiss()
            }
        })
        dialog.show()
    }


    private fun isLastChapter(): Boolean {
        return lessonCount - 1 == mUserData?.inProgressCompletedCount!!
    }

    private fun proceedToNext() {
        if (learnedTopics.isNotEmpty()) {
            binding.dotIndicator.visibility = View.VISIBLE
        }

        var inProgressId = ""
        var completedLesson: UserData.CompletedLesson? = null
        if (isLastPage) {
            if (isLastChapter()) {
                mUserData?.apply {
                    id = DEFAULT_USER_ID
                    inProgressCompletedCount = mUserData?.inProgressCompletedCount!! + 1
                }

                completedLesson = UserData.CompletedLesson(
                    lessonId,
                    UserData.CompletedLesson.LessonName(
                        lessonTitle?.hi!!,
                        lessonTitle?.en!!,
                        lessonTitle?.bn!!,
                        lessonTitle?.te!!
                    )
                )

                if (this::nextLessonId.isInitialized) {
                    mUserData?.apply {
                        id = DEFAULT_USER_ID
                        completedLessons.add(completedLesson)
                        inProgressLessonId = nextLessonId
                        inProgressCompletedCount = 0
                    }
                    inProgressId = nextLessonId
                }
            } else {
                mUserData?.apply {
                    inProgressCompletedCount = mUserData?.inProgressCompletedCount!! + 1
                }
            }

            lifecycleScope.launch {
                userDataUseCase.saveUserData(mUserData!!)
            }

            val intent = Intent(this, AfterLearnActivity::class.java)
            intent.putExtra(AfterLearnActivity.TOTAL_QUIZ, totalQuiz)
            intent.putExtra(AfterLearnActivity.WRONG_QUIZ, wrongQuiz)
            startActivity(intent)
            finish()
        } else {
            binding.learningViewPager.currentItem = binding.learningViewPager.currentItem + 1
        }
    }


    override fun onBackPressed() {
        AppUtils.showQuitLearningDialog(this@LearningActivity, object : DialogCallbacks {
            override fun onPositiveButtonClicked() {
                finish()
            }
        })
    }

    override fun onTranslationDataChanged(rightAnswerCode: String, code: String) {
        rightAnswerCodeTranslation = rightAnswerCode
        answerCodeTranslation = code
        if (rightAnswerCode.isEmpty() && code.isEmpty()) {
            binding.nextBtn.disableButton()
        } else {
            binding.nextBtn.enableButton()
        }
    }

    override fun onOptionsDataChanged(rightCode: Int, code: Int) {
        rightAnswerCodeOptions = rightCode
        answerCodeOptions = code
        if (rightCode == 0 && code == 0) {
            binding.nextBtn.disableButton()
        } else {
            binding.nextBtn.enableButton()
        }
    }
}