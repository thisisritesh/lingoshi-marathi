package com.riteshmaagadh.lla_base.ui.home.revision

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.databinding.RevisionItemBinding
import com.riteshmaagadh.lla_base.ui.home.revision.detail.RevisionDetailActivity
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener

class RevisionsAdapter(private val revisionList: List<UserData.CompletedLesson>, private val context: Context, private val langCode: String) :
    RecyclerView.Adapter<RevisionsAdapter.RevisionVH>() {


    inner class RevisionVH(private val binding: RevisionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(revision: UserData.CompletedLesson, position: Int) {
            binding.apply {
                val lessonName = revision.getLessonName(langCode)
                lessonNoTv.text = context.getString(R.string.lesson_number, (position + 1).toString())
                lessonTitleTv.text = lessonName

                root.setClickListener(object : ViewClickListener {
                    override fun onClicked() {
                        val intent = Intent(context, RevisionDetailActivity::class.java)
                        intent.putExtra(RevisionDetailActivity.LESSON_NAME, revision.lessonName.en)
                        intent.putExtra(RevisionDetailActivity.SCREEN_HEADING_TEXT, revision.getLessonName(langCode))
                        context.startActivity(intent)
                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevisionVH =
        RevisionVH(RevisionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = revisionList.size

    override fun onBindViewHolder(holder: RevisionVH, position: Int) {
        holder.bind(revisionList[position], position)
    }

}