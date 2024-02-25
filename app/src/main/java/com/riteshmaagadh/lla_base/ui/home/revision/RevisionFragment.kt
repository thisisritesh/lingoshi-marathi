package com.riteshmaagadh.lla_base.ui.home.revision

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.databinding.FragmentRevisionBinding
import com.riteshmaagadh.lla_base.domain.usecases.UserDataUseCase
import com.riteshmaagadh.lla_base.utils.AppConstants
import com.riteshmaagadh.lla_base.utils.FragmentCallback
import com.riteshmaagadh.lla_base.utils.Pref
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener
import kotlinx.coroutines.launch


class RevisionFragment : Fragment() {

    private lateinit var binding: FragmentRevisionBinding
    private val pref by lazy { Pref(requireContext()) }
    private lateinit var fragmentCallback: FragmentCallback
    private val userDataUseCase: UserDataUseCase by lazy { UserDataUseCase(requireContext()) }
    private val langCode: String by lazy { Pref(requireContext()).getPrefString(AppConstants.PREFERRED_LANG) }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRevisionBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    private fun initUi() {

        lifecycleScope.launch {
            userDataUseCase.getUserData().observe(viewLifecycleOwner) {
                renderUi(it.completedLessons)
            }
        }

    }

    private fun renderUi(completedLessons: MutableList<UserData.CompletedLesson>) {
        if (completedLessons.isNotEmpty()) {
            binding.revisionRv.adapter = RevisionsAdapter(completedLessons, requireContext(), langCode)
            binding.revisionRv.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        } else {
            binding.revisionRv.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

            binding.goToLessonsBtn.setClickListener(object : ViewClickListener{
                override fun onClicked() {
                    fragmentCallback.openFragmentById(R.id.nav_lessons)
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RevisionFragment()
    }
}