package com.riteshmaagadh.lla_base.ui.learning

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.databinding.LearnedTopicItemBinding
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.playAudio
import com.riteshmaagadh.lla_base.utils.setClickListener

class LearnedTopicsAdapter(private val topics: List<Topic>, private val langCode: String, private val context: Context) :
    RecyclerView.Adapter<LearnedTopicsAdapter.LearnedTopicVH>() {


    inner class LearnedTopicVH(private val binding: LearnedTopicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.contentTv.text = topic.info?.content
            binding.speechTextTv.text = topic.info?.speechTxt
            binding.meaningTv.text = topic.info?.getMeaning(langCode)

            binding.playPauseImageBtn.setClickListener(object : ViewClickListener {
                override fun onClicked() {
                    binding.playPauseImageBtn.playAudio(topic.info?.audio, context)
                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnedTopicVH
        = LearnedTopicVH(LearnedTopicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: LearnedTopicVH, position: Int) {
        holder.bind(topics[position])
    }

}