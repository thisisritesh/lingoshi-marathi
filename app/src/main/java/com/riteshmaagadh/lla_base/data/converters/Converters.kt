package com.riteshmaagadh.lla_base.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.Topic

class Converters {

    @TypeConverter
    fun fromImg(value: Lesson.Img): String {
        val gson = Gson()
        val type = object : TypeToken<Lesson.Img>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toImg(value: String): Lesson.Img {
        val gson = Gson()
        val type = object : TypeToken<Lesson.Img>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromTitle(value: Lesson.Title): String {
        val gson = Gson()
        val type = object : TypeToken<Lesson.Title>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTitle(value: String): Lesson.Title {
        val gson = Gson()
        val type = object : TypeToken<Lesson.Title>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSubLesson(value: List<Lesson.SubLesson>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Lesson.SubLesson>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubLesson(value: String): List<Lesson.SubLesson> {
        val gson = Gson()
        val type = object : TypeToken<List<Lesson.SubLesson>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromTopic(value: List<Topic>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTopic(value: String): List<Topic?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic?>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromInfo(value: Topic.Info?): String {
        val gson = Gson()
        val type = object : TypeToken<Topic.Info?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toInfo(value: String): Topic.Info? {
        val gson = Gson()
        val type = object : TypeToken<Topic.Info?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromIntro(value: Topic.Intro?): String {
        val gson = Gson()
        val type = object : TypeToken<Topic.Intro?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIntro(value: String): Topic.Intro? {
        val gson = Gson()
        val type = object : TypeToken<Topic.Intro?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromOptions(value: Topic.Options?): String {
        val gson = Gson()
        val type = object : TypeToken<Topic.Options?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toOptions(value: String): Topic.Options? {
        val gson = Gson()
        val type = object : TypeToken<Topic.Options?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTitle(value: Topic.Title?): String {
        val gson = Gson()
        val type = object : TypeToken<Topic.Title?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTopicTitle(value: String): Topic.Title? {
        val gson = Gson()
        val type = object : TypeToken<Topic.Title?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTranslation(value: Topic.Translation?): String {
        val gson = Gson()
        val type = object : TypeToken<Topic.Translation?>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTranslation(value: String): Topic.Translation? {
        val gson = Gson()
        val type = object : TypeToken<Topic.Translation?>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromMeaning(value: List<Topic.Info.Meaning?>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Info.Meaning?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMeaning(value: String): List<Topic.Info.Meaning?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Info.Meaning?>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromContent(value: List<Topic.Options.Content?>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Content?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toContent(value: String): List<Topic.Options.Content?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Content?>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromOption(value: List<Topic.Options.Option?>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Option?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toOption(value: String): List<Topic.Options.Option?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Option?>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromOptionTranslation(value: List<Topic.Translation.Option?>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Translation.Option?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toOptionTranslation(value: String): List<Topic.Translation.Option?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Translation.Option?>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDisplayText(value: List<Topic.Options.Option.DisplayText?>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Option.DisplayText?>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDisplayText(value: String): List<Topic.Options.Option.DisplayText?> {
        val gson = Gson()
        val type = object : TypeToken<List<Topic.Options.Option.DisplayText?>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromCompletedLesson(value: List<UserData.CompletedLesson>): String {
        val gson = Gson()
        val type = object : TypeToken<List<UserData.CompletedLesson>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCompletedLesson(value: String): List<UserData.CompletedLesson> {
        val gson = Gson()
        val type = object : TypeToken<List<UserData.CompletedLesson>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLessonName(value: UserData.CompletedLesson.LessonName): String {
        val gson = Gson()
        val type = object : TypeToken<UserData.CompletedLesson.LessonName>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLessonName(value: String): UserData.CompletedLesson.LessonName {
        val gson = Gson()
        val type = object : TypeToken<UserData.CompletedLesson.LessonName>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromQuickLearnDispTxt(value: QuickLearn.DispTxt): String {
        val gson = Gson()
        val type = object : TypeToken<QuickLearn.DispTxt>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toQuickLearnDispTxt(value: String): QuickLearn.DispTxt {
        val gson = Gson()
        val type = object : TypeToken<QuickLearn.DispTxt>() {}.type
        return gson.fromJson(value, type)
    }

}