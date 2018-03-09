package com.example.yona.dictionaryawesome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class Dictionary_Activity extends AppCompatActivity {

    /*private static final String[] WORDS = {
            "Mediocre", "UC Berkely",
            "underachiever", "Stanford A- student",
            "jerk","Marty stepp"
    };*/

    // a dictionary of {word -. definiton} pairs for lookup
     private Map<String,String> dictionary;
     private List<String> words;
    private MediaPlayer mp;
    private int points;
    private int highScore;



    private void readFileData(){
         Scanner scan = new Scanner(getResources().openRawResource(R.raw.dictionarywords));
         readFileHelper(scan);

         try {
             Scanner scan2 = new Scanner(openFileInput("added_word.txt"));
             readFileHelper(scan2);
         } catch (Exception e) { //was FileNotFoundException
             e.printStackTrace();
         }

     }

    private void readFileHelper (Scanner scan) {

        while (scan.hasNextLine()) {
            //"abate - to lessen in intensity or degree"
            String line = scan.nextLine();
            String[] parts = line.split(" ", 2);
            dictionary.put(parts[0], parts[1]);
            words.add(parts[0]);
        }
    }


     public void choosewords(){
         Random randy = new Random();
         int randomindex = randy.nextInt(words.size());
         String theword = words.get(randomindex);
         String theDefn = dictionary.get(theword);

         //all the defifitions stored as arraylist
         List<String> defns = new ArrayList<>(dictionary.values());
         defns.remove(theDefn);
         Collections.shuffle(defns);
         defns = defns.subList(0,4);
         defns.add(theDefn);
         Collections.shuffle(defns);

         TextView tv = (TextView) findViewById(R.id.the_word);
         tv.setText(theword);

         ListView list = (ListView) findViewById(R.id.word_list);
         final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                 this,
                 android.R.layout.simple_list_item_1,
                 defns
                 //new ArrayList<String>(dictionary.keySet())
                 //before in this place was =>WORDS  new ArrayList<String>(dictionary.keySet())
                 // we needed and array as the last perrameter that is my we but it array list
         );
         list.setAdapter(adapter);


     }

    /*
    * this method runs when the app is first loading up.
    * it sets up the dictionary of words and definitions.
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_);

        // converts the array into a map
        /*dictionary = new HashMap<>();
        for(int i =0; i<WORDS.length; i+=2){
            dictionary.put(WORDS[i],WORDS[i+1]);
        }*/

        dictionary = new HashMap<>();
        words = new ArrayList<>();
        readFileData();

        choosewords();

        // put the dictionary words into an adapter so they can be seen in list
        ListView list = (ListView) findViewById(R.id.word_list);
        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(dictionary.keySet())
                //before in this place was =>WORDS  new ArrayList<String>(dictionary.keySet())
                // we needed and array as the last perrameter that is my we but it array list
        );
        list.setAdapter(adapter);*/

        // this is the code that should run when the user taps items in the list
       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // look up definition of the word and display as a toast
                String word = adapterView.getItemAtPosition(i).toString();
                String defn = dictionary.get(word);
                Toast.makeText(getApplicationContext(),defn,  Toast.LENGTH_SHORT).show();
            }
        });*/

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // look up definition of the word and display as a toast
                /*String word = adapterView.getItemAtPosition(i).toString();
                String defn = dictionary.get(word);
                Toast.makeText(getApplicationContext(),defn,  Toast.LENGTH_SHORT).show();*/
                String defnClicked = adapterView.getItemAtPosition(i).toString();

                TextView textview = (TextView) findViewById(R.id.the_word);
                String theword = textview.getText().toString();
                String correctDefn =  dictionary.get(theword);
                if(defnClicked.equals(correctDefn)){
                    points++;
                    if(points>highScore){
                        highScore=points;

                        SharedPreferences prefs = getSharedPreferences(
                                "myprefs", MODE_PRIVATE);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putInt("highScore",highScore);
                        prefsEditor.apply();

                    }
                    Toast.makeText(getApplicationContext(),"Awesome = " +points + "hi =" + highScore,  Toast.LENGTH_SHORT).show();

                }else{
                    points--;
                    Toast.makeText(getApplicationContext(),"Wrong try again = " +points+ "hi =" + highScore,  Toast.LENGTH_SHORT).show();

                }
                choosewords();
            }
        });
        //Load the high Score
        SharedPreferences prefs = getSharedPreferences("myprefs",MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0);


        mp = MediaPlayer.create(this, R.raw.jeopardythemesong);
        mp.start();

    }

    // stops the music once we move away from the game
    @Override
    protected void onPause() {
        super.onPause();

        mp.stop();
    }

    // resume the music once we return
    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("points",points);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getInt("points",0);
    }

    public void addAWorkClick(View view) {
        // go to the add word activity
        Intent intent = new Intent(this,AddWordActivity.class);
        startActivity(intent);
    }
}
