package com.riteshmaagadh.lla_base.ui.home.quicklearn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.databinding.FragmentQuickLearnBinding
import com.riteshmaagadh.lla_base.databinding.FragmentRevisionBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.ui.home.lessons.LessonsFragment
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.FragmentCallback
import com.riteshmaagadh.lla_base.utils.NetworkState
import com.riteshmaagadh.lla_base.utils.Pref


class QuickLearnFragment : Fragment() {

    private lateinit var binding: FragmentQuickLearnBinding
    private val pref by lazy { Pref(requireContext()) }
    private lateinit var fragmentCallback: FragmentCallback
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(requireContext()) }
    private val langCode: String by lazy { Pref(requireContext()).getPrefString(AppConstants.PREFERRED_LANG) }
    private lateinit var quickLearnViewModel: QuickLearnViewModel
    private val quickLearnList: MutableList<QuickLearn> = mutableListOf()
    private lateinit var quickLearnAdapter: QuickLearnAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuickLearnBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    private fun initUi() {
        quickLearnViewModel = ViewModelProvider(this)[QuickLearnViewModel::class.java]

        quickLearnAdapter = QuickLearnAdapter(quickLearnList, langCode, requireContext())
        binding.quickLearnRv.adapter = quickLearnAdapter

        quickLearnViewModel.quickLearnLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    Log.e(TAG, "Loading: ")
                }

                is NetworkState.Failed -> {
                    Log.e(TAG, "Failed: " + it.message)
                }

                is NetworkState.Success -> {
                    Log.e(TAG, "Success: " + it.data.size)
                    quickLearnList.addAll(it.data)
                    quickLearnAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }

                else -> {}
            }
        }

        quickLearnViewModel.getQuickLearn(requireContext())


    }

    companion object {

        private const val TAG = "QuickLearnFragment"

        @JvmStatic
        fun newInstance() =
            QuickLearnFragment()
    }
}