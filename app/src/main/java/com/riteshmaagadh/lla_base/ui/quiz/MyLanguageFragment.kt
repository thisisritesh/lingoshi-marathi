package com.riteshmaagadh.lla_base.ui.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.riteshmaagadh.lla_base.databinding.FragmentMyLanguageBinding

class MyLanguageFragment : Fragment() {

    private lateinit var binding: FragmentMyLanguageBinding
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
        binding = FragmentMyLanguageBinding.inflate(inflater, container, false)
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.radioGroupLanguages.setOnCheckedChangeListener { p0, p1 ->
            callbacks.shouldButtonEnabled()
            val radioBtn = p0.findViewById<RadioButton>(p1)
            callbacks.onLanguageSelected(radioBtn?.tag.toString())
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            MyLanguageFragment()
    }
}