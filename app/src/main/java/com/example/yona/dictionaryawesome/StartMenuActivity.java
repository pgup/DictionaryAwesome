package com.example.yona.dictionaryawesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {

    private static final int REQ_CODE_ADD_WORD = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void playTheGameClick(View view) {
        //go to the Dictionary activity
        Intent intent = new Intent(this,Dictionary_Activity.class);

        startActivity(intent);
    }

    public void addANewWordClick(View view) {
        // go to the addwordactivity
        Intent intent = new Intent(this,AddWordActivity.class);
        intent.putExtra("firstword","whatsup");
       //startActivity(intent);
        startActivityForResult(intent,REQ_CODE_ADD_WORD);
        //
    }

    // get called when addwordActivity finishes and comes back to me
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_ADD_WORD) {
            String newWord = data.getStringExtra("newWord");
            String newDefn = data.getStringExtra("newdefn");

            //Toast.makeText(getApplicationContext(),"Wrong try again",  Toast.LENGTH_SHORT).show();

            Toast.makeText(getApplicationContext(), "you added the word" + newWord, Toast.LENGTH_LONG).show();
        }
    }
}
