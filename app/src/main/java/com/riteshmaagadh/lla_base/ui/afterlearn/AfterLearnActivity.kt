package com.riteshmaagadh.lla_base.ui.afterlearn

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.databinding.ActivityAfterLearnBinding
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.divideToPercent
import com.riteshmaagadh.lla_base.utils.setClickListener

class AfterLearnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAfterLearnBinding

    companion object {
        const val TOTAL_QUIZ = "total_quiz"
        const val WRONG_QUIZ = "wrong_quiz"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityAfterLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalQuiz = intent?.extras?.getInt(TOTAL_QUIZ)
        val wrongQuiz = intent?.extras?.getInt(WRONG_QUIZ)

        binding.accuracyProgressBar.apply {
            max = totalQuiz!!
            progress = (totalQuiz - wrongQuiz!!)
        }

        val accuracyPercentage = "${(totalQuiz!! - wrongQuiz!!).divideToPercent(totalQuiz)} %"

        Toast.makeText(this, accuracyPercentage, Toast.LENGTH_SHORT).show()

        binding.secondaryRemarksTv.text = getString(R.string.after_learn_des, accuracyPercentage)

        binding.nextBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                finish()
            }
        })

        binding.homeBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                finish()
            }
        })


    }
}