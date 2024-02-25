package com.riteshmaagadh.lla_base.ui.home.quicklearn.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riteshmaagadh.lla_base.data.modals.QlPhrase
import com.riteshmaagadh.lla_base.databinding.LearnedTopicItemBinding
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.playAudio
import com.riteshmaagadh.lla_base.utils.setClickListener

class QlPhraseAdapter(private val topics: List<QlPhrase>, private val langCode: String, private val context: Context) :
    RecyclerView.Adapter<QlPhraseAdapter.QlPhraseVH>() {


    inner class QlPhraseVH(private val binding: LearnedTopicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(qlPhrase: QlPhrase) {
            binding.contentTv.text = qlPhrase.disp_txt.mr
            binding.speechTextTv.text = qlPhrase.speech_txt
            binding.meaningTv.text = qlPhrase.getDispTxt(langCode)

            binding.playPauseImageBtn.setClickListener(object : ViewClickListener {
                override fun onClicked() {
                    binding.playPauseImageBtn.playAudio(qlPhrase.audio_url, context)
                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QlPhraseVH = QlPhraseVH(
        LearnedTopicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: QlPhraseVH, position: Int) {
        holder.bind(topics[position])
    }

}