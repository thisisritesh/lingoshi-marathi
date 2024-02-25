package com.riteshmaagadh.lla_base.ui.home.quicklearn.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.QlPhrase
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.databinding.ActivityQuickLearnDetailBinding
import com.riteshmaagadh.lla_base.databinding.ActivityRevisionDetailBinding
import com.riteshmaagadh.lla_base.ui.home.revision.detail.RevisionDetailViewModel
import com.riteshmaagadh.lla_base.ui.learning.LearnedTopicsAdapter
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.NetworkState
import com.riteshmaagadh.lla_base.utils.Pref
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener

class QuickLearnDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuickLearnDetailBinding
    private lateinit var quickLearnDetailViewModel: QuickLearnDetailViewModel
    private lateinit var adapter: QlPhraseAdapter
    private val qlPhrases: MutableList<QlPhrase> = mutableListOf()

    companion object {
        const val QL_TITLE = "ql_title"
        const val DOC_ID = "extras_doc_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityQuickLearnDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView13.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                finish()
            }
        })

        val docId = intent.extras?.getString(DOC_ID)
        val qlTitle = intent.extras?.getString(QL_TITLE)

        binding.screenHeadingTv.text = qlTitle

        val langCode = Pref(this).getPrefString(AppConstants.PREFERRED_LANG)

        adapter = QlPhraseAdapter(qlPhrases, langCode, this)
        binding.topicsRv.adapter = adapter

        quickLearnDetailViewModel = ViewModelProvider(this)[QuickLearnDetailViewModel::class.java]

        quickLearnDetailViewModel.qlPhrasesLiveData.observe(this) {
            when(it){
                is NetworkState.Loading -> {
//                    Log.e(LearningActivity.TAG, "Loading: ")
                }
                is NetworkState.Failed -> {
//                    Log.e(LearningActivity.TAG, "Failed: " + it.message)
                }
                is NetworkState.Success -> {
                    qlPhrases.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        if (docId != null) {
            quickLearnDetailViewModel.getQlPhrases(this, docId)
        }

    }

}