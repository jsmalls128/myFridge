package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private List<Word> mWords = Collections.emptyList();
    private WordViewModel mWordViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);

    }
    public void showFoodsByType(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, fooditems.class);
        startActivity(intent);
    }
    public void showRecipes(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, recipeitems.class);
        String url = "https://www.allrecipes.com/search/results/?wt=";
        for (String s : fooditems.foodMap.keySet()) {
            url += s + " ";
        }
        System.out.println(url);
        Uri webpage = Uri.parse(url);
        // Got to finish Uri before making the Intent
        Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
        PackageManager packageManager=getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent,0);
        boolean isIntentSafe = activities.size()>0;
        if(isIntentSafe){
            startActivity(webIntent);
        }
        else{
            startActivity(intent);
        }

    }

}
