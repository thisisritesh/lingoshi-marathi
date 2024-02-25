package com.riteshmaagadh.lla_base.ui.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshmaagadh.lla_base.data.modals.DailyTarget
import com.riteshmaagadh.lla_base.databinding.FragmentDailyTargetBinding


class DailyTargetFragment : Fragment() {

    private lateinit var binding: FragmentDailyTargetBinding
    private lateinit var targetsAdapter: DailyTargetsAdapter
    private lateinit var callbacks: QuizCallbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callbacks = context as QuizCallbacks
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyTargetBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        val targets: MutableList<DailyTarget> = mutableListOf()
        targets.add(DailyTarget("5 mins / day","Relax"))
        targets.add(DailyTarget("10 mins / day","Normal"))
        targets.add(DailyTarget("15 mins / day","Serious"))
        targets.add(DailyTarget("30 mins / day","Great"))
        targets.add(DailyTarget("60 mins / day","Awesome"))


        targetsAdapter = DailyTargetsAdapter(targets, object : DailyTargetsAdapter.OnTargetSelected {
            override fun onTargetSelected(target: DailyTarget) {
                targetsAdapter.setTarget(target.target)
                callbacks.shouldButtonEnabled()
            }
        })

        binding.targetsRv.adapter = targetsAdapter

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DailyTargetFragment()
    }
}