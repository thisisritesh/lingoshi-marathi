package com.riteshmaagadh.lla_base.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riteshmaagadh.lla_base.data.modals.Onboarding
import com.riteshmaagadh.lla_base.databinding.OnboardingItemBinding

class OnboardingAdapter(
    private val onboardingList: List<Onboarding>,
    private val context: Context
) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingVH>() {


    inner class OnboardingVH(private val binding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboarding: Onboarding) {
            binding.imageView.setImageResource(onboarding.imageResId)
            binding.descriptionTv.text = context.getString(onboarding.txtResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingVH = OnboardingVH(
        OnboardingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = onboardingList.size

    override fun onBindViewHolder(holder: OnboardingVH, position: Int) {
        holder.bind(onboardingList[position])
    }

}