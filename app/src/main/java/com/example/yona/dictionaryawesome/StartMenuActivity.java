package com.example.yona.dictionaryawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void playTheGameClick(View view) {
        //go to the Dictionary activity
    }

    public void addANewWordClick(View view) {
        // go to the addwordactivity
        Intent intent = new Intent(this,AddWordActivity.class);
        intent.putExtra("firstword","whatsup");
        startActivity(intent);
    }
}
