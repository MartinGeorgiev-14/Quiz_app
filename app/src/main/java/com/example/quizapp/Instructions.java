package com.example.quizapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class Instructions extends Dialog {

    private int instructionPoints = 0;

    public Instructions(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_layout);

        final AppCompatButton continueButton = findViewById(R.id.continueButton);
        final TextView instructions = findViewById(R.id.instructions);

        setInstructionPoint(instructions, "You will get one point for every correct answer");
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void setInstructionPoint(TextView instructions, String instructionPoint)
    {
        if (instructionPoints == 0){
            instructions.setText(instructionPoint);
        }
        else{
            instructions.setText(instructions.getText()+"\n\n" + instructionPoint);
        }
    }
}
