package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizResult extends AppCompatActivity {

    private List<QuestionsList> questionsLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        final TextView score = findViewById(R.id.score);
        final TextView totalScore = findViewById(R.id.totalScore);
        final TextView correct = findViewById(R.id.correctScore);
        final TextView incorrect = findViewById(R.id.incorrectScore);
        final AppCompatButton shareButton = findViewById(R.id.shareButton);
        final AppCompatButton reTakeButton = findViewById(R.id.retakeQuizButton);

        //Getting questions list from MainActivity
        questionsLists = (List<QuestionsList>)getIntent().getSerializableExtra("questions");

        totalScore.setText("/" + questionsLists.size());
        score.setText(getCorrectAnswers() + "");
        correct.setText(getCorrectAnswers() + "");
        incorrect.setText(String.valueOf(questionsLists.size() - getCorrectAnswers()));

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open and share intent
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "My score = " + score.getText());

                Intent shareIntent = Intent.createChooser(sendIntent, "Share Via");
                startActivity(shareIntent);
            }
        });

        reTakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Restart the quiz to MainActivity
                startActivity(new Intent(QuizResult.this, MainActivity.class));
                finish();
            }
        });
    }

    private int getCorrectAnswers()
    {
        int correctAnswer = 0;

        for (int i = 0; i < questionsLists.size(); i++)
        {
            int getUserSelectedOption = questionsLists.get(i).getUserSelectedAnswer(); //gets user selected option
            int getQuestionAnswer = questionsLists.get(i).getAnswer(); //gets the correct answer for the question

            //check of the user's selected answer if it matches with the correct answer
            if (getQuestionAnswer == getUserSelectedOption)
            {
                correctAnswer++;
            }
        }

        return correctAnswer;
    }
}