package com.example.recipebook;

import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CookingRecipe {


    private String title;
    private String content;
    private int rating;
    private int id;

    public CookingRecipe(String title, String content, int rating){
        this.title = title;
        this.content = content;
        this.rating = rating;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return "title: " + title ;
    }
}
