package com.riteshmaagadh.lla_base.ui.home.lessons

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.databinding.LessonItemCompletedBinding
import com.riteshmaagadh.lla_base.databinding.LessonItemProgressBinding
import com.riteshmaagadh.lla_base.databinding.LessonItemUpcomingBinding
import com.riteshmaagadh.lla_base.ui.learning.LearningActivity
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener


class LessonsAdapter(
    private val lessons: List<Lesson>,
    private val context: Context,
    val langCode: String,
    val callbacks: AdapterCallbacks
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var completedLessonIds: MutableList<String> = mutableListOf()
    private var inProgressLessonId = ""
    private var inProgressCompletedCount = 0


    interface AdapterCallbacks {
        fun onInProgressClicked(
            lesson: Lesson, titleJson: String, lessons: List<Lesson>, position: Int
        )
    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.setIsRecyclable(false)
    }


    fun setCompletedLessonsIds(ids: List<String>) {
        completedLessonIds.addAll(ids)
    }

    fun setInProgressDetails(lessonId: String, completedCount: Int) {
        inProgressLessonId = lessonId
        inProgressCompletedCount = completedCount
    }

    inner class CompletedLessonVH(private val binding: LessonItemCompletedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson, position: Int) {
            binding.lessonTitleTv.text = lesson.getTitle(langCode)

            Glide.with(context).load(lesson.img.enabled).into(binding.lessonImageView)

            binding.constraintLayout.setClickListener(object : ViewClickListener {
                override fun onClicked() {
                    val titleJson = convertTitleToString(lesson.title)
                    val intent = Intent(context, LearningActivity::class.java)
                    intent.putExtra(LearningActivity.SUB_LESSONS_COUNT, lesson.sublessons.size)
                    intent.putExtra(LearningActivity.LESSON_ID, lesson.lessonId)
                    intent.putExtra(LearningActivity.LESSON_NAME, titleJson)
                    context.startActivity(intent)
                }
            })

            if (lessons.size - 1 == position) {
                binding.bottomRod.visibility = View.INVISIBLE
            }

        }
    }

    private fun convertTitleToString(title: Lesson.Title): String {
        return Gson().toJson(title)
    }

    inner class InProgressLessonVH(private val binding: LessonItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson, position: Int) {
            binding.lessonTitleTv.text = lesson.getTitle(langCode)

            Glide.with(context).load(lesson.img.enabled).into(binding.lessonImageView)

            binding.circularProgressIndicator.apply {
                max = lesson.sublessons.size
                progress = inProgressCompletedCount
            }

            binding.constraintLayout.setClickListener(object : ViewClickListener {
                override fun onClicked() {
                    val titleJson = convertTitleToString(lesson.title)
                    callbacks.onInProgressClicked(lesson, titleJson, lessons, position)
                }
            })

            if (lessons.size - 1 == position) {
                binding.bottomRod.visibility = View.INVISIBLE
            }

        }
    }


    inner class UpcomingLessonVH(private val binding: LessonItemUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: Lesson, position: Int) {
            binding.lessonTitleTv.text = lesson.getTitle(langCode)

            Glide.with(context).load(lesson.img.disabled).into(binding.lessonImageView)

            if (lessons.size - 1 == position) {
                binding.bottomRod.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.please_complete_the_previous_lesson_first),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                CompletedLessonVH(LessonItemCompletedBinding.inflate(layoutInflater, parent, false))
            }

            1 -> {
                InProgressLessonVH(LessonItemProgressBinding.inflate(layoutInflater, parent, false))
            }

            2 -> {
                UpcomingLessonVH(LessonItemUpcomingBinding.inflate(layoutInflater, parent, false))
            }

            else -> {
                UpcomingLessonVH(LessonItemUpcomingBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

    override fun getItemCount(): Int = lessons.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val lessonId = lessons[position].lessonId

        for (id in completedLessonIds) {
            if (id == lessonId) {
                val completedViewHolder = holder as CompletedLessonVH
                completedViewHolder.bind(lessons[position], position)
                return
            }
        }

        if (completedLessonIds.isEmpty() && position == 0) {
            val inProgressViewHolder = holder as InProgressLessonVH
            inProgressViewHolder.bind(lessons[position], position)
            return
        }

        if (inProgressLessonId == lessonId) {
            val inProgressViewHolder = holder as InProgressLessonVH
            inProgressViewHolder.bind(lessons[position], position)
            return
        } else {
            val upcomingViewHolder = holder as UpcomingLessonVH
            upcomingViewHolder.bind(lessons[position], position)
            return
        }

    }

    override fun getItemViewType(position: Int): Int {
        val lessonId = lessons[position].lessonId
        for (id in completedLessonIds) {
            if (id == lessonId) {
                return 0;
            }
        }
        if (inProgressLessonId == lessonId) {
            return 1;
        }

        if (completedLessonIds.isEmpty() && position == 0) {
            return 1;
        }

        return 2;
    }

}