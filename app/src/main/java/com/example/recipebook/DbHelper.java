package com.example.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import  com.example.recipebook.CookingRecipe;

import com.example.recipebook.Provider.MyContentProvider;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private ContentResolver myCR;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    public static final String TABLE_PRODUCTS = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_RATING = "rating";


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();



    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TITLE
                + " TEXT," + COLUMN_CONTENT + " TEXT," + COLUMN_RATING + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public void add(CookingRecipe cookingRecipe) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, cookingRecipe.getTitle());
        values.put(COLUMN_CONTENT, cookingRecipe.getContent());
        values.put(COLUMN_RATING,cookingRecipe.getRating());
        myCR.insert(MyContentProvider.CONTENT_URI, values);
    }


    public List<CookingRecipe> find(String recipeTitle,int rating) {
        String selection = "";
        List<CookingRecipe> recipeList = new ArrayList<>();
        String[] projection = {COLUMN_ID,COLUMN_TITLE, COLUMN_CONTENT,COLUMN_RATING };
        if(!recipeTitle.isEmpty()){
            if(rating <= 0){
                selection = "title LIKE \"" + recipeTitle + "%\"";
            }else{
                selection = "title LIKE \"" + recipeTitle + "%\"" + "AND rating = \"" + rating + "\"" ;

            }
        }else if(rating != 0){
            selection = "rating = \"" + rating + "\"";
        }

        Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI,
                projection, selection.isEmpty() ? null : selection, null,
                null);


        if (cursor.moveToFirst()) {
            do {
                CookingRecipe recipe = new CookingRecipe(cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
                recipe.setId(Integer.parseInt(cursor.getString(0)));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }


        return recipeList;
    }

    public CookingRecipe findById(int id){
        CookingRecipe recipe = null;
        String selection = "";
        List<CookingRecipe> recipeList = new ArrayList<>();
        String[] projection = {COLUMN_ID,COLUMN_TITLE, COLUMN_CONTENT,COLUMN_RATING };
        if(id != 0){
            selection = "id = \"" + id + "\"";
        }

        Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI,
                projection, selection.isEmpty() ? null : selection, null,
                null);



        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            recipe = new CookingRecipe(cursor.getString(1),cursor.getString(2),Integer.parseInt(cursor.getString(3)));
            recipe.setId(Integer.parseInt(cursor.getString(0)));
        }

        return recipe;
    }

    public boolean deleteProduct(int id){
        boolean result = false;
        String selection = "id = \"" + id + "\"";
        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI,
                selection, null);
        if (rowsDeleted>0){
            result = true;
        }

        return  result;
    }

    public void updateProduct(int id,int rating){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RATING, rating);
        String selection = "id = \"" + id + "\"";
        myCR.update(MyContentProvider.CONTENT_URI,values,selection,null);

    }


}
