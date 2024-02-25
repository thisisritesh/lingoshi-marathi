package com.riteshmaagadh.lla_base.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.databinding.ActivityHomeBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.home.account.AccountFragment
import com.riteshmaagadh.lla_base.ui.home.lessons.LessonsFragment
import com.riteshmaagadh.lla_base.ui.home.quicklearn.QuickLearnFragment
import com.riteshmaagadh.lla_base.ui.home.revision.RevisionFragment
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.FragmentCallback
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), FragmentCallback {

    private lateinit var binding: ActivityHomeBinding
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppUtils.blackStatusBar(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener(listener)
            setOnItemReselectedListener { }
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment_container, LessonsFragment.newInstance())
            .commit()

        lifecycleScope.launch {
            userDataUseCase.getUserData().observe(this@HomeActivity) {
                if (it == null) {
                    lifecycleScope.launch {
                        userDataUseCase.saveUserData(
                            UserData(
                                AppConstants.DEFAULT_USER_ID,
                                "",
                                0,
                                0,
                                3,
                                5,
                                mutableListOf()
                            )
                        )
                    }
                }
            }
        }

    }


    private val listener =
        NavigationBarView.OnItemSelectedListener { menuItem: MenuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.nav_lessons -> {
                    selectedFragment = LessonsFragment.newInstance()
                }

                R.id.nav_revision -> {
                    selectedFragment = RevisionFragment.newInstance()
                }

                R.id.nav_quick_learn -> {
                    selectedFragment = QuickLearnFragment.newInstance()
                }

                R.id.nav_account -> {
                    selectedFragment = AccountFragment.newInstance()
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, selectedFragment!!)
                .commit()
            true
        }

    override fun openFragmentById(id: Int) {
        binding.bottomNavigationView.selectedItemId = id
    }


}