package com.riteshmaagadh.lla_base.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.databinding.FragmentInsightsBinding


class InsightsFragment : Fragment() {

    private lateinit var binding: FragmentInsightsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInsightsBinding.inflate(inflater, container, false)
        return binding.root
    }






    companion object {
        @JvmStatic
        fun newInstance() =
            InsightsFragment()
    }
}