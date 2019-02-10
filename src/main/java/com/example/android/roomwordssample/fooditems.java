package com.example.android.roomwordssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fooditems extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private WordViewModel mWordViewModel;
    public static ArrayList<Word> foodList = new ArrayList<Word>();
    public static HashMap<String,String> foodMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fooditems);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final WordListAdapter adapter = new WordListAdapter(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Get a new or existing ViewModel from the ViewModelProvider.
            mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

            // Add an observer on the LiveData returned by getAlphabetizedWords.
            // The onChanged() method fires when the observed data changes and the activity is
            // in the foreground.
            mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
                @Override
                public void onChanged(@Nullable final List<Word> words) {
                    // Update the cached copy of the words in the adapter.
                    adapter.setWords(words);
                }
            });

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(fooditems.this, NewWordActivity.class);
                    startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                }
            });
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY),"Protein",data.getStringExtra(NewWordActivity.EXTRA_QUANTITY));
                mWordViewModel.insert(word);
                foodList.add(word);
                foodMap.put(word.getWord(),word.getQuantity());
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        }
    }


