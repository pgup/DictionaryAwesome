package com.example.yona.dictionaryawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Intent intent = getIntent();
        String word = intent.getStringExtra("firstword");

        EditText setWordEditText = (EditText) findViewById(R.id.new_word);
        setWordEditText.setText(word);
    }

    public void addThisWordClick(View view) {
        //add the given word/defn to dictioary
        EditText newWord = (EditText) findViewById(R.id.new_word);
        EditText newDefn = (EditText) findViewById(R.id.new_defn);

        try {
            //SAME AS PRINTWRITER BOTHE WRITE TO FILE "OUT" IS THE SAME AS SUSTEM.OUT.PRINTLN
            PrintStream output = new PrintStream(openFileOutput("added_word.txt", MODE_PRIVATE | MODE_APPEND));
            output.println(newWord + " - " + newDefn);
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // go back to start menu


    }

}
