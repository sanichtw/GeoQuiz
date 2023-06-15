package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.model.Question

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_africa, false),
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var userRightAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate()")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        updateQuestion()



        trueButton.setOnClickListener {
            checkAnswer(true)
            updateButtonsState(false)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            updateButtonsState(false)

        }
        nextButton.setOnClickListener {
            nextQuestion()
        }
        prevButton.setOnClickListener {
            prevQuestion()
        }
        questionTextView.setOnClickListener {
            nextQuestion()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy()")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun nextQuestion() {
        if ((currentIndex + 1) >= questionBank.size) {
            Toast.makeText(
                this@MainActivity,
                "The game has been ended. Your result is ${showResult()}%",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            currentIndex = (currentIndex + 1) % questionBank.size

            updateQuestion()
            updateButtonsState(true)
        }
    }

    private fun prevQuestion() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            (currentIndex - 1) % questionBank.size
        }

        updateQuestion()
        updateButtonsState(true)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        var messageResId: Int

        if (correctAnswer == userAnswer) {
            messageResId = R.string.correct_toast
            userRightAnswers += 1
        } else {
            messageResId = R.string.incorrect_toast
        }

        Toast.makeText(this@MainActivity, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun updateButtonsState(state: Boolean) {
        trueButton.isEnabled = state
        falseButton.isEnabled = state
    }

    private fun showResult(): Double {
        var result = Math.floor((userRightAnswers.toFloat() / questionBank.size.toFloat() * 100).toDouble())
        return result
    }

    companion object {
        private const val TAG = "Test"
    }
}