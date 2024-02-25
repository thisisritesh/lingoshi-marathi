package com.riteshmaagadh.lla_base.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.home.HomeActivity
import com.riteshmaagadh.lla_base.ui.onboarding.OnboardingActivity
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.switchLocale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        setContentView(R.layout.activity_splash)


        lifecycleScope.launch {
            delay(2000)
            if (FirebaseAuth.getInstance().currentUser == null) {
                FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@SplashActivity, "Auth failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
        }


    }
}