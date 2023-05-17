package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout optionOneLayout, optionTwoLayout, optionThreeLayout, optionFourLayout;
    private TextView optionOne, optionTwo, optionThree, optionFour;
    private ImageView optionOneIcon, optionTwoIcon, optionThreeIcon, optionFourIcon;

    private TextView totalQuestion;
    private TextView currenQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionOneLayout = findViewById(R.id.optionOneLayout);
        optionTwoLayout = findViewById(R.id.optionTwoLayout);
        optionThreeLayout = findViewById(R.id.optionThreeLayout);
        optionFourLayout = findViewById(R.id.optionFourLayout);

        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);

        optionOneIcon = findViewById(R.id.optionOneIcon);
        optionTwoIcon = findViewById(R.id.optionTwoIcon);
        optionThreeIcon = findViewById(R.id.optionThreeIcon);
        optionFourIcon = findViewById(R.id.optionFourIcon);

        totalQuestion = findViewById(R.id.totalQuestion);
        currenQuestion = findViewById(R.id.currentQuestion);

        final AppCompatButton nextButton = findViewById(R.id.nextButton);

    }
}