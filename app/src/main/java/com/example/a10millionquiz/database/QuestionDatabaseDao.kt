package com.example.a10millionquiz.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {
    @Insert
    fun insertQuestion(question: Question)

    @Insert
    fun insertAnswer(answer: Answer)

    @Update
    fun updateQuestion(question: Question)

    @Update
    fun updateAnswer(answer: Answer)

    @Query("SELECT * FROM question ORDER BY id")
    fun getQuestions(): MutableList<Question>

    @Query("SELECT * FROM question ORDER BY id DESC LIMIT 1")
    fun getLast(): Question?

    @Query("SELECT * FROM answer WHERE question_id = :questionId")
    fun getAnswers(questionId : Long): List<Answer>

}