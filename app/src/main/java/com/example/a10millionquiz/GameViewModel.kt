package com.example.a10millionquiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.example.a10millionquiz.database.Answer
import com.example.a10millionquiz.database.Question
import com.example.a10millionquiz.database.QuestionDatabaseDao
import com.google.android.material.internal.ContextUtils.getActivity
import kotlin.random.Random

enum class NavigationDestination {
    SHOW_GAME_OVER, SHOW_VICTORY
}

class GameViewModel(
    private val dao: QuestionDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val START_SCORE: Int = 10000
        private const val QUESTIONS_AMOUNT: Int = 10
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _currentQuestion = MutableLiveData<String>()
    val currentQuestion: LiveData<String> get() = _currentQuestion

    private val _currentQuestionNumber = MutableLiveData<Int>()
    val currentQuestionNumber: LiveData<Int> get() = _currentQuestionNumber

    private val _destination = MutableLiveData<NavigationDestination?>(null)
    val destination: LiveData<NavigationDestination?> get() = _destination

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private val _questionAnswers = MutableLiveData<List<Answer>>(null)
    val questionAnswers: LiveData<List<Answer>> get() = _questionAnswers

    private lateinit var questions: MutableList<Question>
    private var currentBets: List<Int> = listOf(0, 0, 0, 0)

    init {
        _score.value = START_SCORE
        _currentQuestionNumber.value = 0
        _destination.value = null
        loadQuestionsList()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _destination.value = NavigationDestination.SHOW_GAME_OVER
            }
        }
        nextQuestion()
        resetBets()
    }

    private fun resetBets() {
        currentBets = mutableListOf(0, 0, 0, 0)
    }

    private fun nextQuestion() {
        val question = questions.removeAt(0)
        _currentQuestion.value = question.text
        _currentQuestionNumber.value = _currentQuestionNumber.value?.plus(1)
        _questionAnswers.value = dao.getAnswers(question.questionId)
        timer.start()
    }

    private fun recalculateScore() {
        for (i in currentBets.indices)
            if (!_questionAnswers.value?.get(i)!!.correct)
                _score.value = (_score.value)?.minus(currentBets[i])
    }

    private fun loadQuestionsList() {
        val seed = System.currentTimeMillis()
        questions = dao.getQuestions().shuffled().toMutableList()
    }

    fun onSubmit() {
        timer.cancel()
        recalculateScore()
        if (_score.value!! <= 0) {
            _destination.value = NavigationDestination.SHOW_GAME_OVER
            return
        }
        if (currentQuestionNumber.value == QUESTIONS_AMOUNT) {
            _destination.value = NavigationDestination.SHOW_VICTORY
            return
        }
        nextQuestion()
    }


    fun validateBets(usersInpit: List<String>): Boolean {
        if (!usersInpit.all { input ->
                if (input.isNullOrEmpty()) false else input.all {
                    Character.isDigit(it)
                }
            })
            return false
        val bets = usersInpit.map { it.toInt() }
        if (bets.all { it >= 0L } && bets.sum() == _score.value) {
            currentBets = bets
            return true
        }
        return false
    }
}