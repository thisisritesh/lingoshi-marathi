package com.riteshmaagadh.lla_base.utils


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.riteshmaagadh.lla_base.R
import com.riteshmaagadh.lla_base.databinding.WarningDialogBinding


object AppUtils {

    fun blackStatusBar(activity: Activity) {
        activity.window.statusBarColor =
            activity.resources.getColor(R.color.bg_color, activity.theme);
    }


    fun showComingSoonDialog(
        context: Context
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = WarningDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.negativeBtn.visibility = View.GONE
        binding.negativeBtn.setOnClickListener { dialog.dismiss() }
        binding.titleTv.text = context.getString(R.string.coming_soon)
        binding.descriptionTv.text = context.getString(R.string.coming_soon_des)
        binding.positiveBtn.text = context.getString(R.string.okay)
        binding.positiveBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    fun showNoAudioDialog(
        context: Context
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = WarningDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.negativeBtn.visibility = View.GONE
        binding.negativeBtn.setOnClickListener { dialog.dismiss() }
        binding.titleTv.text = context.getString(R.string.voice_not_available)
        binding.descriptionTv.text = context.getString(R.string.no_audio_des)
        binding.positiveBtn.text = context.getString(R.string.okay)
        binding.positiveBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    fun showQuitLearningDialog(
        context: Context,
        dialogCallbacks: DialogCallbacks
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = WarningDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.negativeBtn.setOnClickListener { dialog.dismiss() }
        binding.titleTv.text = context.getString(R.string.quitting_learning)
        binding.descriptionTv.text = context.getString(R.string.quitting_learning_des)
        binding.positiveBtn.text = context.getString(R.string.okay)
        binding.negativeBtn.text = context.getString(R.string.no)
        binding.positiveBtn.setOnClickListener {
            dialog.dismiss()
            dialogCallbacks.onPositiveButtonClicked()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }



}