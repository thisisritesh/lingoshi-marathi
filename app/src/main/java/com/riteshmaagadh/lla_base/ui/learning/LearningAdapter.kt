package com.riteshmaagadh.lla_base.ui.learning

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.databinding.LessonInfoItemBinding
import com.riteshmaagadh.lla_base.databinding.LessonIntroItemBinding
import com.riteshmaagadh.lla_base.databinding.LessonOptionsItemBinding
import com.riteshmaagadh.lla_base.databinding.LessonTranslationItemBinding
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.playAudio
import com.riteshmaagadh.lla_base.utils.setClickListener
import org.w3c.dom.Text

class LearningAdapter(private val topics: List<Topic>, private val preferredLang: String, private val mContext: Context, private val callbacks: AdapterCallbacks) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AdapterCallbacks {
        fun onTranslationDataChanged(rightAnswerCode: String, code: String)
        fun onOptionsDataChanged(rightCode: Int, code: Int)
    }


    inner class LessonIntroVH(private val binding: LessonIntroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.apply {
                titleTv.text = topic.getTitle(preferredLang)
                chapterIntroTv.text = topic.getIntro(preferredLang)
            }
        }
    }

    inner class LessonInfoVH(private val binding: LessonInfoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.apply {
                if (topic.info!!.img.isEmpty()) {
                    infoImageView.visibility = View.GONE
                } else {
                    Glide.with(mContext)
                        .load(topic.info!!.img)
                        .into(infoImageView)
                }

                titleTv.text = topic.getTitle(preferredLang)

                wordPhraseTv.text = topic.info!!.content
                speechTextTv.text = topic.info!!.speechTxt
                meaningTextTv.text = topic.info!!.getMeaning(preferredLang)

                playPauseImageBtn.setClickListener(object : ViewClickListener {
                    override fun onClicked() {
                        binding.playPauseImageBtn.playAudio(topic.info?.audio, mContext)
                    }
                })
            }
        }
    }


    inner class LessonOptionsVH(private val binding: LessonOptionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.apply {
                titleTv.text = topic.getTitle(preferredLang)

                option1Rb.text = topic.options!!.options[0].getDisplayText(preferredLang)
                option1Rb.tag = topic.options!!.options[0].code
                option2Rb.text = topic.options!!.options[1].getDisplayText(preferredLang)
                option2Rb.tag = topic.options!!.options[1].code
                option3Rb.text = topic.options!!.options[2].getDisplayText(preferredLang)
                option3Rb.tag = topic.options!!.options[2].code
                wordPhraseTv.text = topic.options!!.content.displayTxt

                radioGroupOptions.setOnCheckedChangeListener { radioGroup, checkedId ->
                    val checkedBtn: RadioButton = radioGroup.findViewById(checkedId)
                    callbacks.onOptionsDataChanged(topic.options!!.rightCode, checkedBtn.tag as Int)
                }


                playPauseImageBtn.setClickListener(object : ViewClickListener {
                    override fun onClicked() {
                        playPauseImageBtn.playAudio(topic.options?.content?.audio, mContext)
                    }
                })

            }
        }
    }


    inner class LessonTranslationVH(private val binding: LessonTranslationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var answerCode = ""
        fun bind(topic: Topic) {
            binding.apply {
                titleTv.text = topic.getTitle(preferredLang)

                val translation = topic.translation

                wordPhraseTv.text = translation!!.getDisplayText(preferredLang)

                if (translation.options.size >= 1) {
                    if (translation.options[0].displayText.isNullOrEmpty()) {
                        word1Tv.visibility = View.GONE
                    } else {
                        word1Tv.text = translation.options[0].displayText
                        word1Tv.tag = translation.options[0].code
                        word1Tv.visibility = View.VISIBLE
                        word1Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word1Tv)
                                addToAnswer(word1Tv, translation.rightCode)
                            }
                        })
                    }
                }

                if (translation.options.size >= 2) {
                    if (translation.options[1].displayText.isNullOrEmpty()) {
                        word2Tv.visibility = View.GONE
                    } else {
                        word2Tv.text = translation.options[1].displayText
                        word2Tv.tag = translation.options[1].code
                        word2Tv.visibility = View.VISIBLE
                        word2Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word2Tv)
                                addToAnswer(word2Tv, translation.rightCode)
                            }
                        })
                    }
                }

                if (translation.options.size >= 3) {
                    if (translation.options[2].displayText.isNullOrEmpty()) {
                        word3Tv.visibility = View.GONE
                    } else {
                        word3Tv.text = translation.options[2].displayText
                        word3Tv.tag = translation.options[2].code
                        word3Tv.visibility = View.VISIBLE
                        word3Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word3Tv)
                                addToAnswer(word3Tv, translation.rightCode)
                            }
                        })
                    }
                }

                if (translation.options.size >= 4) {
                    if (translation.options[3].displayText.isNullOrEmpty()) {
                        word4Tv.visibility = View.GONE
                    } else {
                        word4Tv.text = translation.options[3].displayText
                        word4Tv.tag = translation.options[3].code
                        word4Tv.visibility = View.VISIBLE
                        word4Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word4Tv)
                                addToAnswer(word4Tv, translation.rightCode)
                            }
                        })
                    }
                }

                if (translation.options.size >= 5) {
                    if (translation.options[4].displayText.isNullOrEmpty()) {
                        word5Tv.visibility = View.GONE
                    } else {
                        word5Tv.text = translation.options[4].displayText
                        word5Tv.tag = translation.options[4].code
                        word5Tv.visibility = View.VISIBLE
                        word5Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word5Tv)
                                addToAnswer(word5Tv, translation.rightCode)
                            }
                        })
                    }
                }

                if (translation.options.size >= 6) {
                    if (translation.options[5].displayText.isNullOrEmpty()) {
                        word6Tv.visibility = View.GONE
                    } else {
                        word6Tv.text = translation.options[5].displayText
                        word6Tv.tag = translation.options[5].code
                        word6Tv.visibility = View.VISIBLE

                        word6Tv.setClickListener(object : ViewClickListener {
                            override fun onClicked() {
                                disableOption(word6Tv)
                                addToAnswer(word6Tv, translation.rightCode)
                            }
                        })
                    }
                }

                resetBtn.setClickListener(object : ViewClickListener {
                    override fun onClicked() {
                        enableOption(word1Tv)
                        enableOption(word2Tv)
                        enableOption(word3Tv)
                        enableOption(word4Tv)
                        enableOption(word5Tv)
                        enableOption(word6Tv)
                        answerTv.text = ""
                        answerCode = ""
                        callbacks.onTranslationDataChanged("",answerCode)
                    }
                })

            }
        }

        @SuppressLint("SetTextI18n")
        private fun addToAnswer(optionTv: TextView, rightAnswerCode: String) {
            answerCode += optionTv.tag
            callbacks.onTranslationDataChanged(rightAnswerCode, answerCode)
            binding.answerTv.text = "${binding.answerTv.text} ${optionTv.text}"
        }

        private fun disableOption(optionTv: TextView) {
            optionTv.setBackgroundResource(R.drawable.disable_translation_options_bg)
            optionTv.setTextColor(mContext.getColor(R.color.card_color))
            optionTv.isClickable = false
            optionTv.isEnabled = false
        }

        private fun enableOption(optionTv: TextView) {
            optionTv.setBackgroundResource(R.drawable.secondary_btn_bg_small)
            optionTv.setTextColor(mContext.getColor(R.color.primary_color))
            optionTv.isClickable = true
            optionTv.isEnabled = true
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when (topics[position].layoutType) {
            "intro" -> 0
            "info" -> 1
            "options" -> 2
            "translation" -> 3
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> LessonIntroVH(LessonIntroItemBinding.inflate(layoutInflater, parent, false))
            1 -> LessonInfoVH(LessonInfoItemBinding.inflate(layoutInflater, parent, false))
            2 -> LessonOptionsVH(LessonOptionsItemBinding.inflate(layoutInflater, parent, false))
            3 -> LessonTranslationVH(
                LessonTranslationItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            else -> null!!
        }
    }

    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topic = topics[position]
        when (topic.layoutType) {
            "intro" -> {
                (holder as LessonIntroVH).bind(topic)
            }

            "info" -> {
                (holder as LessonInfoVH).bind(topic)
            }

            "options" -> {
                (holder as LessonOptionsVH).bind(topic)
            }

            "translation" -> {
                (holder as LessonTranslationVH).bind(topic)
            }
        }
    }


}