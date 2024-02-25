package com.riteshmaagadh.lla_base.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.riteshmaagadh.lla_base.data.converters.Converters
import com.riteshmaagadh.lla_base.data.dao.LessonsDao
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.entities.UserData
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.Sublesson
import com.riteshmaagadh.lla_base.data.modals.Topic

@TypeConverters(Converters::class)
@Database(version = 1, entities = [Lesson::class, Topic::class, UserData::class, QuickLearn::class], exportSchema = false)
abstract class LessonsDatabase : RoomDatabase() {

    abstract fun lessonsDao(): LessonsDao

    companion object {
        @Volatile
        private var instance: LessonsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            LessonsDatabase::class.java, "lingoshi-marathi.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}