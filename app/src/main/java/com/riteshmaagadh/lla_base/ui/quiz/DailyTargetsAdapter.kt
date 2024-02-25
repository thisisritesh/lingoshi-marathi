package com.riteshmaagadh.lla_base.ui.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.data.modals.DailyTarget
import com.riteshmaagadh.lla_base.databinding.DailyTargetBinding

class DailyTargetsAdapter(
    private val targets: List<DailyTarget>,
    private val onTargetSelected: OnTargetSelected
) :
    RecyclerView.Adapter<DailyTargetsAdapter.DailyTargetVH>() {

    interface OnTargetSelected {
        fun onTargetSelected(target: DailyTarget)
    }

    private var selectedTarget = "0"

    inner class DailyTargetVH(private val binding: DailyTargetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(target: DailyTarget) {

            binding.targetTv.text = target.target
            binding.remarksTv.text = target.remarks

            if (selectedTarget == target.target) {
                binding.targetTv.setTextColor(binding.root.context.getColor(R.color.primary_color))
                binding.remarksTv.setTextColor(binding.root.context.getColor(R.color.primary_color))
                binding.root.setBackgroundResource(R.drawable.selected_radio_bg)
            } else {
                binding.targetTv.setTextColor(binding.root.context.getColor(R.color.secondary_text_color))
                binding.remarksTv.setTextColor(binding.root.context.getColor(R.color.secondary_text_color))
                binding.root.setBackgroundResource(R.drawable.unselected_radio_bg)
            }

            binding.root.setOnClickListener {
                onTargetSelected.onTargetSelected(target)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyTargetVH =
        DailyTargetVH(
            DailyTargetBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun getItemCount(): Int = targets.size

    override fun onBindViewHolder(holder: DailyTargetVH, position: Int) {
        holder.bind(targets[position])
    }

    fun setTarget(target: String) {
        this.selectedTarget = target
        notifyDataSetChanged()
    }

}