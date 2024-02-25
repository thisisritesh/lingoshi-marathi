package com.riteshmaagadh.lla_base.utils


import android.content.Context
import android.content.SharedPreferences
import com.riteshmaagadh.lla_base.R


class Pref(val context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name) + "riteshmaagadh", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    public fun getPrefString(key: String) : String {
        return sharedPreferences.getString(key,"")!!
    }

    public fun putPref(key: String, value: String) {
        editor.putString(key,value)
        editor.commit()
    }

    public fun getPrefBoolean(key: String) : Boolean {
        return sharedPreferences.getBoolean(key,false)
    }

    public fun putPref(key: String, value: Boolean) {
        editor.putBoolean(key,value)
        editor.commit()
    }

    public fun getPrefInt(key: String) : Int {
        return sharedPreferences.getInt(key,0)
    }

    public fun putPref(key: String, value: Int) {
        editor.putInt(key,value)
        editor.commit()
    }

    public fun getPrefStringSet(key: String) : MutableSet<String>? {
        return sharedPreferences.getStringSet(key, setOf())
    }

    public fun putPref(key: String, value: MutableSet<String>) {
        editor.putStringSet(key,value)
        editor.commit()
    }




}
