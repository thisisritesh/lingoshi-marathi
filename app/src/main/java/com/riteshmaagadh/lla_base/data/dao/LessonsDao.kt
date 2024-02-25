package com.riteshmaagadh.lla_base.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.Sublesson
import com.riteshmaagadh.lla_base.data.modals.Topic
import java.math.BigDecimal

@Dao
interface LessonsDao {

    @Query("SELECT * FROM lesson")
    fun getLessons() : List<Lesson>

    @Insert
    fun saveLessonsData(lessons: List<Lesson>)

    @Insert
    fun saveQuickLearn(quickLearn: List<QuickLearn>)

    @Query("SELECT * FROM topic WHERE sublessonId = :id")
    fun getSubLessons(id: String) : List<Topic>

    @Query("SELECT * FROM topic WHERE lesson_name = :name")
    fun getSubLessonsByLessonName(name: String) : List<Topic>

    @Query("SELECT * FROM lesson WHERE title_en = :name")
    fun getLessonByLessonName(name: String) : Lesson

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveSubLessons(topics: List<Topic?>?)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserData(userId: Int) : LiveData<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserData(userData: UserData)

    @Query("SELECT * FROM quick_learn")
    fun getQuickLearn() : List<QuickLearn>

}