package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.geoquiz.viewmodel.QuizViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private var userRightAnswers = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "savedInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndexFromState = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndexFromState

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_button)

        updateQuestionText()
        getBtnsState()


        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        trueButton.setOnClickListener {
            checkAnswer(true)
            updateBtnsState(false)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            updateBtnsState(false)
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestionText()
            updateBtnsState(true)
        }
        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestionText()
            updateBtnsState(true)

        }
        questionTextView.setOnClickListener {
            updateQuestionText()
        }
    }

    private fun updateQuestionText() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int

        if (correctAnswer == userAnswer) {
            messageResId = R.string.correct_toast
            userRightAnswers += 1
        } else {
            messageResId = R.string.incorrect_toast
        }

        Toast.makeText(this@MainActivity, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun getBtnsState() {
        trueButton.isEnabled = quizViewModel.getBtnsState()
        falseButton.isEnabled = quizViewModel.getBtnsState()
    }

    private fun updateBtnsState(state: Boolean) {
        trueButton.isEnabled = quizViewModel.updateBtnsState(state)
        falseButton.isEnabled = quizViewModel.updateBtnsState(state)
    }

//    private fun showResult(): Double {
//        return Math.floor((userRightAnswers.toFloat() / quizViewModel.currentQuestionsSize * 100).toDouble())
//    }

    companion object {
        private const val TAG = "Test"
        private const val KEY_INDEX = "index"
        private const val REQUEST_CODE_CHEAT = 0
    }
}