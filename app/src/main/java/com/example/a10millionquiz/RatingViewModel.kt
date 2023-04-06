package com.example.a10millionquiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.a10millionquiz.database.QuestionDatabaseDao

class RatingViewModel(
    val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    val rating = dao.getRating()
}