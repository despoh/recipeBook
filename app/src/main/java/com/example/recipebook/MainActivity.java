package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.recipebook.Provider.MyContentProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecycleViewAdapter adapter;
    RecyclerView recycleView;
    SearchView searchbar;
    String currentSearchString;
    int currentRating;
    DbHelper dbHelper;
    List<CookingRecipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSearchString = "";
        currentRating = 0;


        dbHelper = new DbHelper(this, null, null, 1);
        recipes.addAll(dbHelper.find(currentSearchString,currentRating));
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        searchbar = (SearchView) findViewById(R.id.searchView);
        adapter = new RecycleViewAdapter(this, recipes);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recipes.clear();
                currentSearchString = s;
                recipes.addAll(dbHelper.find(s,currentRating));
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recipes.clear();
                currentSearchString = s;
                recipes.addAll(dbHelper.find(s,currentRating));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        recipes.clear();
        recipes.addAll(dbHelper.find(currentSearchString,currentRating));
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String[] ratingsArray = new String[]{"All Recipe","⭐","⭐⭐","⭐⭐⭐","⭐⭐⭐⭐","⭐⭐⭐⭐⭐"};

        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();


        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,ratingsArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("Sort");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentRating = i;
                recipes.clear();
                recipes.addAll(dbHelper.find(currentSearchString,currentRating));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.createRecipeMenuButton:
                Intent intent = new Intent(this,CreateRecipeActivity.class);
                startActivity(intent);

            default: return super.onOptionsItemSelected(item);
        }



    }
}

