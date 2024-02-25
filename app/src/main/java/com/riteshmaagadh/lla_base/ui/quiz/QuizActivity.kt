package com.riteshmaagadh.lla_base.ui.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.databinding.ActivityQuizBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.home.HomeActivity
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.FragmentAdapter
import com.riteshmaagadh.lla_base.utils.Pref
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.disableButton
import com.riteshmaagadh.lla_base.utils.enableButton
import com.riteshmaagadh.lla_base.utils.setClickListener
import com.riteshmaagadh.lla_base.utils.switchLocale
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity(), QuizCallbacks {

    private lateinit var binding: ActivityQuizBinding
    private var currentPosition = 0
    private var languageCode: String = "en"
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = FragmentAdapter(supportFragmentManager, this.lifecycle)
        adapter.addFragment(MyLanguageFragment.newInstance())
        adapter.addFragment(ReasonFragment.newInstance())
        adapter.addFragment(DailyTargetFragment.newInstance())

        binding.viewPagerQuiz.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPagerQuiz.isUserInputEnabled = false

        binding.viewPagerQuiz.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                currentPosition = position
                binding.progressBarQuiz.progress = currentPosition
                binding.continueBtn.disableButton()
            }
        })


        binding.viewPagerQuiz.adapter = adapter


        binding.continueBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                if (currentPosition == 2) {
                    Pref(this@QuizActivity).putPref(AppConstants.PREFERRED_LANG, languageCode)
                    switchLocale(this@QuizActivity)
                    binding.progressBarQuiz.progress = currentPosition + 1
                    startActivity(Intent(this@QuizActivity, HomeActivity::class.java))
                    finish()
                } else {
                    binding.viewPagerQuiz.setCurrentItem(binding.viewPagerQuiz.currentItem + 1, true)
                }
            }
        })


    }

    override fun shouldButtonEnabled() {
        binding.continueBtn.enableButton()
    }

    override fun onLanguageSelected(langCode: String) {
        languageCode = langCode
    }

    override fun onReasonSelected(reason: String) {
    }

    override fun onDailyGoalSelected(dailyGoalInMin: Int) {
    }


}