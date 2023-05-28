package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.local.QueryEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // creating questions list
    private final List<QuestionsList> questionsLists = new ArrayList<>();
    private RelativeLayout optionOneLayout, optionTwoLayout, optionThreeLayout, optionFourLayout;
    private TextView optionOne, optionTwo, optionThree, optionFour;
    private ImageView optionOneIcon, optionTwoIcon, optionThreeIcon, optionFourIcon;

    private TextView questionTitle;
    private TextView totalQuestion;
    private TextView currenQuestion;

    // Database refrence from Url
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizz-app-37928-default-rtdb.firebaseio.com/");

    //Current question position set by default 0
    private int currentQuestionPossition = 0;

    //Selected option num. The value must be between 1 and 4. 0 = Options is not selected
    private int selectedOption = 0;
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

        questionTitle = findViewById(R.id.questionTitle);
        totalQuestion = findViewById(R.id.totalQuestion);
        currenQuestion = findViewById(R.id.currentQuestion);

        final AppCompatButton nextButton = findViewById(R.id.nextButton);

        // Show instruct dialog
        Instructions instructions = new Instructions(MainActivity.this);
        instructions.setCancelable(false);
        instructions.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instructions.show();

        // Getting data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot questions : snapshot.child("questions").getChildren())
                {
                    String getQuestion = questions.child("question").getValue(String.class);
                    String getOptionOne = questions.child("option1").getValue(String.class);
                    String getOptionTwo = questions.child("option2").getValue(String.class);
                    String getOptionThee = questions.child("option3").getValue(String.class);
                    String getOptionFour = questions.child("option4").getValue(String.class);
                    int getAnswer = Integer.parseInt(questions.child("answer").getValue(String.class));

                    //Creating questions list object and adding details
                    QuestionsList questionsList = new QuestionsList(getQuestion, getOptionOne, getOptionTwo, getOptionThee, getOptionFour, getAnswer);
                    //Adding questionsList object into the list
                    questionsLists.add(questionsList);
                }
                //Setting the total questions to the textview
                totalQuestion.setText("/"+questionsLists.size());

                selectQuestion(currentQuestionPossition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to get data from the database!!", Toast.LENGTH_SHORT).show();
            }
        });

        optionOneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 1;

                //select option
                selectOption(optionOneLayout, optionOneIcon);
            }
        });
        optionTwoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 2;

                //select option
                selectOption(optionTwoLayout, optionTwoIcon);
            }
        });
        optionThreeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 3;

                //select option
                selectOption(optionThreeLayout, optionThreeIcon);
            }
        });
        optionFourLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = 4;

                //select option
                selectOption(optionFourLayout, optionFourIcon);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            //Check if user has selected an option
            @Override
            public void onClick(View view)
            {
                if (selectedOption != 0)
                {
                    //Set user selected answer
                    questionsLists.get(currentQuestionPossition).setUserSelectedAnswer(selectedOption);

                    //Reset the selected option to default value
                    selectedOption = 0;
                    currentQuestionPossition++;

                    // Check if the list has more questions
                    if (currentQuestionPossition < questionsLists.size())
                    {
                        selectQuestion(currentQuestionPossition);
                    }
                    else
                    {
                        //The list has no questions left
                        finishQuiz();
                    }
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    //creating intent to open QuizResult
    private void finishQuiz(){
        Intent intent = new Intent(MainActivity.this, QuizResult.class);
        //Creating bundle to pass questionList
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable)questionsLists);

        //Add bundle to intent
        intent.putExtras(bundle);

        //Start activity to open QuizResult
        startActivity(intent);

        //Destroy current activity
        finish();
    }

    private void selectQuestion(int questionListPosition)
    {
        //Reset the options for the new question
        resetOptions();
        //getting and setting question details to textView
        questionTitle.setText(questionsLists.get(questionListPosition).getQuestion());
        optionOne.setText(questionsLists.get(questionListPosition).getOptionOne());
        optionTwo.setText(questionsLists.get(questionListPosition).getOptionTwo());
        optionThree.setText(questionsLists.get(questionListPosition).getOptionThree());
        optionFour.setText(questionsLists.get(questionListPosition).getOptionFour());

        //Setting current question view to textView
        currenQuestion.setText("Question"+(questionListPosition+1));
    }

    private void resetOptions()
    {
        optionOneLayout.setBackgroundResource(R.drawable.question_round);
        optionTwoLayout.setBackgroundResource(R.drawable.question_round);
        optionThreeLayout.setBackgroundResource(R.drawable.question_round);
        optionFourLayout.setBackgroundResource(R.drawable.question_round);


        optionOneIcon.setImageResource(0);
        optionTwoIcon.setImageResource(0);
        optionThreeIcon.setImageResource(0);
        optionFourIcon.setImageResource(0);


    }

    private void selectOption(RelativeLayout selectedOptionLayout, ImageView selectedOptionIcon)
    {
        //reset the selected option in order to select a new one
        resetOptions();

        selectedOptionIcon.setImageResource(R.drawable.checkblue);
        selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected);
    }
}