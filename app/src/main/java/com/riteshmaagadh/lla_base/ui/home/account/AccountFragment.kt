package com.riteshmaagadh.lla_base.ui.home.account

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.riteshmaagadh.lla_base.databinding.FragmentAccountBinding
import com.riteshmaagadh.lla_base.utils.AppUtils
import com.riteshmaagadh.lla_base.utils.Config
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.openCustomTab
import com.riteshmaagadh.lla_base.utils.setClickListener


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.dailyGoalParent.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                AppUtils.showComingSoonDialog(requireContext())
            }
        })

        binding.appSettingsParent.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                AppUtils.showComingSoonDialog(requireContext())
            }
        })
        binding.privacyPolicyParent.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                val customIntent = CustomTabsIntent.Builder()
                openCustomTab(
                    requireActivity(),
                    customIntent.build(),
                    Uri.parse(Config.PRIVACY_POLICY_URL)
                )
            }
        })
        binding.aboutUsParent.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                val customIntent = CustomTabsIntent.Builder()
                openCustomTab(
                    requireActivity(),
                    customIntent.build(),
                    Uri.parse(Config.ABOUT_US_URL)
                )
            }
        })
        binding.contactUsParent.setClickListener(object : ViewClickListener {
            override fun onClicked() {
                val customIntent = CustomTabsIntent.Builder()
                openCustomTab(
                    requireActivity(),
                    customIntent.build(),
                    Uri.parse(Config.CONTACT_US_URL)
                )
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment()
    }
}