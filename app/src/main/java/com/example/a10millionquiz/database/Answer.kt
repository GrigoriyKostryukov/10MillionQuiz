package com.example.a10millionquiz.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    /*foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["id"],
        childColumns = ["question_id"]
    )],*/
    tableName = "answer"
)
data class Answer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "answer_id")
    var questionId: Long = 0L,

    @ColumnInfo(name = "question_id")
    var text: Long = 0L,

    @ColumnInfo(name = "answer_text")
    var answer: String = "",

    @ColumnInfo(name = "correct")
    var correct: Boolean = false

)