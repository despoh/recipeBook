package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

public class RecipeDetailActivity extends AppCompatActivity {

    TextView contentTextView;
    TextView titleTextView;
    RatingBar ratingBar;
    DbHelper dbHelper;
    CookingRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        contentTextView = (TextView) findViewById(R.id.content_detail);
        titleTextView = (TextView) findViewById(R.id.title_detail);
        ratingBar = (RatingBar) findViewById(R.id.rating_detail);

        dbHelper = new DbHelper(this,null,null,1);

        int id = getIntent().getIntExtra("recipeId",0);

        recipe = dbHelper.findById(id);

        contentTextView.setText(recipe.getContent());
        titleTextView.setText(recipe.getTitle());

        ratingBar.setRating((float)recipe.getRating());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("pondo",recipe.getId()+"");
        setRatingBarListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.deleteMenuButton:
                dbHelper.deleteProduct(recipe.getId());
                onBackPressed();



            case android.R.id.home: onBackPressed();


            default: return super.onOptionsItemSelected(item);

        }
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//        super.onBackPressed();
//    }

    public void setRatingBarListener(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                dbHelper.updateProduct(recipe.getId(),(int)v);
            }
        });
    }




}
