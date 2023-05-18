package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<QuestionsList> questionsLists = new ArrayList<>();
    private RelativeLayout optionOneLayout, optionTwoLayout, optionThreeLayout, optionFourLayout;
    private TextView optionOne, optionTwo, optionThree, optionFour;
    private ImageView optionOneIcon, optionTwoIcon, optionThreeIcon, optionFourIcon;

    private TextView totalQuestion;
    private TextView currenQuestion;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://quizz-app-37928-default-rtdb.firebaseio.com/");
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

        Instructions instructions = new Instructions(MainActivity.this);
        instructions.setCancelable(false);
        instructions.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        instructions.show();
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

                    QuestionsList questionsList = new QuestionsList(getQuestion, getOptionOne, getOptionTwo, getOptionThee, getOptionFour, getAnswer);
                    questionsLists.add(questionsList);
                }

                totalQuestion.setText("/"+questionsLists.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to get data from the database!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}