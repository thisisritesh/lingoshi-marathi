package com.riteshmaagadh.lla_base.ui.streak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.databinding.ActivityStreakBinding
import com.riteshmaagadh.lla_base.utils.AppUtils

class StreakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStreakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityStreakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextBtn.setOnClickListener {
            finish()
        }

    }
}