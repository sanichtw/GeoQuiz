package com.example.geoquiz

import android.os.Bundle
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

    private var userRightAnswers = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        updateQuestionText()
        getBtnsState()

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
    }
}