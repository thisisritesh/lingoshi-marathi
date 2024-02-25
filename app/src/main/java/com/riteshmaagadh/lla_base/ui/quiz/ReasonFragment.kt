package com.riteshmaagadh.lla_base.ui.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.riteshmaagadh.lla_base.databinding.FragmentReasonBinding


class ReasonFragment : Fragment() {

    private lateinit var binding: FragmentReasonBinding
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
        binding = FragmentReasonBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.radioGroupReasons.setOnCheckedChangeListener { p0, p1 ->
            callbacks.shouldButtonEnabled()
//            callbacks.onReasonSelected(p0?.tag.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReasonFragment()
    }
}