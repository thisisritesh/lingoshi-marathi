package com.riteshmaagadh.lla_base.ui.home.lessons

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.databinding.FragmentLessonsBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.learning.LearningActivity
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.NetworkState
import com.riteshmaagadh.lla_base.utils.Pref
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LessonsFragment : Fragment() {

    private lateinit var binding: FragmentLessonsBinding
    private lateinit var lessonsAdapter: LessonsAdapter
    private val pref by lazy { Pref(requireContext()) }
    private lateinit var lessonsViewModel: LessonsViewModel
    private var lessonsList: MutableList<Lesson> = mutableListOf()
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(requireContext()) }
    private var inProgressCompletedCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonsBinding.inflate(inflater, container, false)
        lessonsViewModel = ViewModelProvider(this)[LessonsViewModel::class.java]
        initUI()
        return binding.root
    }

    private fun initUI() {

        lessonsAdapter = LessonsAdapter(
            lessonsList,
            requireContext(),
            pref.getPrefString(AppConstants.PREFERRED_LANG),
            object : LessonsAdapter.AdapterCallbacks {
                override fun onInProgressClicked(
                    lesson: Lesson,
                    titleJson: String,
                    lessons: List<Lesson>,
                    position: Int
                ) {
                    val intent = Intent(context, LearningActivity::class.java)
                    intent.putExtra(LearningActivity.SUB_LESSONS_COUNT, lesson.sublessons.size)
                    intent.putExtra(LearningActivity.LESSON_ID, lesson.lessonId)
                    intent.putExtra(
                        LearningActivity.SUB_LESSON_ID,
                        lesson.sublessons[inProgressCompletedCount].sublesson_id
                    )
                    intent.putExtra(LearningActivity.LESSON_NAME, titleJson)
                    if (lessons.size - 1 > position) {
                        intent.putExtra(
                            LearningActivity.NEXT_LESSON_ID,
                            lessons[position + 1].lessonId
                        )
                    }
                    startActivity(intent)
                }
            })
        binding.lessonsRv.adapter = lessonsAdapter

        lessonsViewModel.lessonsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {

                }

                is NetworkState.Failed -> {

                }

                is NetworkState.Success -> {
                    lessonsList.addAll(it.data)
                    lessonsAdapter.notifyDataSetChanged()
                }

                else -> {}
            }
        }

        lessonsViewModel.getLessons(requireContext())

    }


    private fun processUserData(data: UserData?) {
        if (data != null) {
            inProgressCompletedCount = data.inProgressCompletedCount
            val completedIds: MutableList<String> = mutableListOf()

            for (item in data.completedLessons) {
                completedIds.add(item.lessonId)
            }

            lessonsAdapter.setCompletedLessonsIds(completedIds)
            if (completedIds.isEmpty() && lessonsList.isNotEmpty()) {
                lessonsAdapter.setInProgressDetails(
                    lessonsList[0].lessonId,
                    data.inProgressCompletedCount
                )
            } else {
                lessonsAdapter.setInProgressDetails(
                    data.inProgressLessonId,
                    data.inProgressCompletedCount
                )
            }
            lessonsAdapter.notifyDataSetChanged()


            setUpProgressBar(data)
//            setUpStreak(data)
//            setUpAvailableLives(data)

            binding.progressBar.visibility = View.GONE
            binding.viewGroup.visibility = View.VISIBLE


            // Scroll to in progress item
            lifecycleScope.launch {
                delay(1000)
                val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_ANY
                    }
                }

                lessonsList.forEachIndexed { index, lesson ->
                    if (lesson.lessonId == data.inProgressLessonId) {
                        smoothScroller.targetPosition = index

                        binding.lessonsRv.layoutManager?.startSmoothScroll(smoothScroller)
                    }
                }
            }

        }
    }

    private fun setUpProgressBar(userData: UserData?) {
        if (userData != null) {
            if (userData.completedLessons.isNotEmpty()) {
                binding.linearProgressIndicator.visibility = View.VISIBLE
                val totalChaptersCount = lessonsList.size
                binding.linearProgressIndicator.max = totalChaptersCount
                binding.linearProgressIndicator.progress = userData.completedLessons.size
            } else {
                binding.linearProgressIndicator.visibility = View.GONE
            }
        }
    }

    private fun setUpAvailableLives(userData: UserData?) {
        if (userData != null) {
            val livesCount = userData.availableLivesCount
            if (livesCount > 0) {
                binding.heartIv.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.heart_red
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.livesCountTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.heart_red
                    )
                )
            } else {
                binding.heartIv.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.livesCountTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_color
                    )
                )
            }
            binding.livesCountTv.text = livesCount.toString()
        }
    }

    private fun setUpStreak(userData: UserData?) {
        if (userData != null) {
            val streakCount = userData.streakCount
            if (streakCount > 0) {
                binding.streakIv.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.fire_orange
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.streakCountTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.fire_orange
                    )
                )
            } else {
                binding.streakIv.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.streakCountTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_color
                    )
                )
            }
            binding.streakCountTv.text = streakCount.toString()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
//        setPrefsAndNotifyAdapter()
        lifecycleScope.launch {
            delay(1000)
            userDataUseCase.getUserData().observe(viewLifecycleOwner) {
                processUserData(it)
            }
        }
    }


    companion object {

        private const val TAG = "LessonsFragment"

        @JvmStatic
        fun newInstance() =
            LessonsFragment()
    }
}