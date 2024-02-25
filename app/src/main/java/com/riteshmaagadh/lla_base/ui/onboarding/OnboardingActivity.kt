package com.riteshmaagadh.lla_base.ui.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.Onboarding
import com.riteshmaagadh.lla_base.databinding.ActivityOnboardingBinding
import com.riteshmaagadh.lla_base.ui.home.HomeActivity
import com.riteshmaagadh.lla_base.ui.quiz.QuizActivity
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.ViewPagerIndicatorAdapter
import com.riteshmaagadh.lla_base.utils.setClickListener


class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "OnboardingActivity"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingList: MutableList<Onboarding> = mutableListOf()
        onboardingList.add(Onboarding(R.string.onboarding_1, R.drawable.kids_playing_monopoly))
        onboardingList.add(Onboarding(R.string.onboarding_2, R.drawable.video_calling_with_friends))
        onboardingList.add(Onboarding(R.string.onboarding_3, R.drawable.organizing_projects))
        onboardingList.add(Onboarding(R.string.onboarding_4, R.drawable.boy_throwing_a_dart))

        val onboardingAdapter = OnboardingAdapter(onboardingList, this)


        val indicatorAdapter = ViewPagerIndicatorAdapter(onboardingList.size)
        binding.indicatorRv.adapter = indicatorAdapter
        binding.viewPagerOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorAdapter.selectItem(position)
            }
        })

        binding.viewPagerOnboarding.adapter = onboardingAdapter

        binding.getStartedBtn.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                startActivity(Intent(this@OnboardingActivity, QuizActivity::class.java))
                finish()
            }
        })

    }




}