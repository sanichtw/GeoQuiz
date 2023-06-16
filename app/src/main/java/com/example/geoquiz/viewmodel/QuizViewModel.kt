package com.example.geoquiz.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import com.example.geoquiz.model.Question

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_africa, false),
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var buttonsState = true

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionsSize: Int
        get() = questionBank.size

    fun moveToNext() {
        if ((currentIndex + 1) <= questionBank.size) {
            currentIndex = (currentIndex + 1) % questionBank.size
        }
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            (currentIndex - 1) % questionBank.size
        }
    }

    fun updateBtnsState(state: Boolean): Boolean {
        buttonsState = state
        return buttonsState
    }

    fun getBtnsState(): Boolean {
        return buttonsState
    }
}