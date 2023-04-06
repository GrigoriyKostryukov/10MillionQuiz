package com.example.a10millionquiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.a10millionquiz.database.QuestionDatabaseDao
import com.example.a10millionquiz.database.UserResult

class VictoryViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var _username = ""

    init {

    }

    fun onSaveResult(username: String, result: Int) {
        val currentResult = UserResult(name = username, maxScore = result)
        val previousUserResult = dao.getResultByName(username)
        if (previousUserResult == null) {
            dao.insertUserResult(currentResult)
            return
        }
        if (previousUserResult.maxScore > currentResult.maxScore)
            dao.updateUserResult(currentResult)
    }

}