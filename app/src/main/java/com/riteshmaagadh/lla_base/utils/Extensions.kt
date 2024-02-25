package com.riteshmaagadh.lla_base.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.Query
import com.riteshmaagadh.lla_base.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Locale


@SuppressLint("ClickableViewAccessibility")
fun View.setClickListener(callback: ViewClickListener) {
    this.setOnTouchListener(View.OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val scaleDownX = ObjectAnimator.ofFloat(
                    this,
                    "scaleX", 0.95f
                )
                val scaleDownY = ObjectAnimator.ofFloat(
                    this,
                    "scaleY", 0.95f
                )
                scaleDownX.duration = 200
                scaleDownY.duration = 200
                val scaleDown = AnimatorSet()
                scaleDown.play(scaleDownX).with(scaleDownY)
                scaleDown.start()
            }

            MotionEvent.ACTION_UP -> {
                val scaleDownX2 = ObjectAnimator.ofFloat(
                    this, "scaleX", 1f
                )
                val scaleDownY2 = ObjectAnimator.ofFloat(
                    this, "scaleY", 1f
                )
                scaleDownX2.duration = 200
                scaleDownY2.duration = 200
                val scaleDown2 = AnimatorSet()
                scaleDown2.play(scaleDownX2).with(scaleDownY2)
                scaleDown2.start()
                callback.onClicked()
            }
        }
        true
    })
}


fun Int.divideToPercent(divideTo: Int): Int {
    return if (divideTo == 0) 0
    else (this / divideTo.toFloat() * 100).toInt()
}


fun openCustomTab(activity: Activity, customTabsIntent: CustomTabsIntent, uri: Uri) {
    val packageName = "com.android.chrome"
    val chromeTargetVersion = 45
    var isSupportCustomTab = false
    try {
        var chromeVersion: String = activity.applicationContext.packageManager
            .getPackageInfo(packageName, 0).versionName
        if (chromeVersion.contains(".")) {
            chromeVersion = chromeVersion.substring(0, chromeVersion.indexOf('.'))
        }
        isSupportCustomTab = Integer.valueOf(chromeVersion) >= chromeTargetVersion
    } catch (ex: PackageManager.NameNotFoundException) {
    } catch (ex: Exception) {
    }

    if (Patterns.WEB_URL.matcher(uri.toString()).matches()){
        if (isSupportCustomTab) {
            try {
                customTabsIntent.intent.setPackage(packageName)
                customTabsIntent.launchUrl(activity, uri)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(activity,"Google Chrome not installed!", Toast.LENGTH_SHORT).show()
            }
        } else {
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    } else {
        Toast.makeText(activity,uri.toString(), Toast.LENGTH_SHORT).show()
    }
}

fun ImageView.playAudio(audioUrl: String?, context: Context) {
    if (audioUrl.isNullOrEmpty()) {
        AppUtils.showNoAudioDialog(context)
    } else {
        this.setImageResource(R.drawable.pause_solid)
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(audioUrl)

        mediaPlayer.setOnCompletionListener {
            this.setImageResource(R.drawable.play_solid)
        }
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
    }
}


fun TextView.enableButton() {
    background = this.context.getDrawable(R.drawable.primary_btn_bg)
    isEnabled = true
}

fun TextView.disableButton() {
    background = this.context.getDrawable(R.drawable.disabled_button_bg)
    isEnabled = false
}

fun playRightAnswerSound(context: Context) {
    MediaPlayer.create(context, R.raw.right).start()
}

fun playWrongAnswerSound(context: Context) {
    MediaPlayer.create(context, R.raw.wrong).start()
}

fun playSuccessAnswerSound(context: Context) {
    MediaPlayer.create(context, R.raw.success).start()
}

fun switchLocale(activity: Activity) {
    val langCode = Pref(activity).getPrefString(AppConstants.PREFERRED_LANG)
    val appLocale = LocaleListCompat.forLanguageTags(langCode)
    AppCompatDelegate.setApplicationLocales(appLocale)
}

inline fun <reified T> getCollection(colRef: Query) = flow {
    val task = colRef.get()
    val result = kotlin.runCatching { Tasks.await(task) }

    val data = result.getOrNull()?.documents?.map {
        it.toObject(T::class.java)
    }
    emit(data)
}


