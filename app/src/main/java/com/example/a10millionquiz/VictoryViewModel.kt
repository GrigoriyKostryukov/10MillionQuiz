package com.example.a10millionquiz

import android.app.Application
import android.util.Log
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
        val previousUserResult = dao.getResultByName(username)


        if (previousUserResult == null) {
            dao.insertUserResult( UserResult(name = username, maxScore = result))
            return
        }
        val currentResult = UserResult(id = previousUserResult.id, name = username, maxScore = result)
        if (previousUserResult.maxScore < currentResult.maxScore) {
            Log.v("KOOOOOOOOOOOOOOOOOOOOOO", currentResult.maxScore.toString())
            dao.updateUserResult(currentResult)
        }
    }

}