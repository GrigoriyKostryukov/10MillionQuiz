package com.example.a10millionquiz.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_result", indices = [Index(value = ["name"], unique = true)])
class UserResult(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Long = 0L,

    @ColumnInfo(name = "max_score")
    var maxScore: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = ""
)