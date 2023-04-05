package com.example.a10millionquiz.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Answer::class, Question::class], version = 1, exportSchema = true)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun getQuestionDatabaseDao() : QuestionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionDatabase? = null

        fun getInstance(context: Context): QuestionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        QuestionDatabase::class.java, "question_db")
                        .allowMainThreadQueries()
                        .createFromAsset("questions.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}

