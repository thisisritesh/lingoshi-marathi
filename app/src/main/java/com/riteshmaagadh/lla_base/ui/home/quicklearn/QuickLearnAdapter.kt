package com.riteshmaagadh.lla_base.ui.home.quicklearn

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.databinding.QuickLearnItemBinding
import com.riteshmaagadh.lla_base.ui.home.quicklearn.detail.QuickLearnDetailActivity
import com.riteshmaagadh.lla_base.utils.ViewClickListener
import com.riteshmaagadh.lla_base.utils.setClickListener

class QuickLearnAdapter(
    private val quickLearnList: List<QuickLearn>,
    private val langCode: String,
    private val context: Context
) : RecyclerView.Adapter<QuickLearnAdapter.QuickLearnVH>() {


    inner class QuickLearnVH(private val binding: QuickLearnItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quickLearn: QuickLearn) {
            binding.quickLearnTitleTv.text = quickLearn.getDispTxt(langCode)
            Glide.with(context)
                .load(quickLearn.ic_url)
                .into(binding.imageView4)


            binding.root.setClickListener(object : ViewClickListener {
                override fun onClicked() {
                    val intent = Intent(context, QuickLearnDetailActivity::class.java)
                    intent.putExtra(QuickLearnDetailActivity.DOC_ID, quickLearn.doc_id)
                    intent.putExtra(QuickLearnDetailActivity.QL_TITLE, quickLearn.getDispTxt(langCode))
                    context.startActivity(intent)
                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickLearnVH = QuickLearnVH(
        QuickLearnItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = quickLearnList.size

    override fun onBindViewHolder(holder: QuickLearnVH, position: Int) {
        holder.bind(quickLearnList[position])
    }

}