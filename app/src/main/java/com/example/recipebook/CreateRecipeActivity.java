package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateRecipeActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText contentEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = (EditText) findViewById(R.id.recipeTitleeEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_recipe_activity_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.saveMenuButton:
                DbHelper dbHelper = new DbHelper(this, null, null, 1);
                CookingRecipe recipe = new CookingRecipe(titleEditText.getText().toString(),contentEditText.getText().toString(),0);
                dbHelper.add(recipe);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

            case android.R.id.home: onBackPressed();



            default: return super.onOptionsItemSelected(item);

        }


    }

}
