package edu.monash.fit4039.fit4039ass2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by nathan on 8/4/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //declare variables
    public static final String DATABASE_NAME = "MonsterDB";
    public static final int DATABASE_VERSION = 1;

    //constructor
    public DatabaseHelper(Context context)
    {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //override onCreate method
    //create the table firstly
    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(Monster.CREATE_STATEMENT);
    }

    //override onUpgrade method
    //drop table if exists
    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Monster.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //method to insert a row in database
    public void insert (ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(Monster.TABLE_NAME, null, values);
        db.close();
    }

    //method to update one/more rows in database by given clauses
    public void update(ContentValues values, String where, String[]whereArgs){
        SQLiteDatabase db = getWritableDatabase();
        db.update(Monster.TABLE_NAME, values, where, whereArgs);
    }

    //method to delete a row by name
    public void delete(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Monster.TABLE_NAME, Monster.COLUMN_NAME + " = ?", new String[]{name});
    }

    //select a monster by name in the database
    public Monster select(String name)
    {
        ArrayList<Monster> monsters = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Monster.TABLE_NAME + " WHERE " + Monster.COLUMN_NAME + " = ?", new String[]{name});
        if (cursor.moveToFirst())
        {
            do {
                Monster monster = new Monster (cursor.getLong(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getInt(5));
                monsters.add(monster);
            } while(cursor.moveToNext());
        }
        if (monsters.size() > 0)
            return monsters.get(0);
        else
            return null;
    }

    //get all monsters and return a hashmap object
    public HashMap<Long, Monster> GetAllMonsters()
    {
        HashMap<Long, Monster> monsters = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Monster.TABLE_NAME, null);

        if (cursor.moveToFirst())
        {
            do {
                Monster monster = new Monster (cursor.getLong(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getInt(5));
                monsters.put(monster.get_Id(), monster);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return monsters;
    }

    //get the last inserted monster and return the monster
    public Monster getLastMonster()
    {
        ArrayList<Monster> monsters = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Monster.TABLE_NAME + " WHERE " +
                Monster.COLUMN_ID + " = (SELECT MAX( " + Monster.COLUMN_ID + " ) FROM " + Monster.TABLE_NAME + " )", null);
        if (cursor.moveToFirst())
        {
            //read the value of a row and set a corrsponding monster object
            do {
                Monster monster = new Monster (cursor.getLong(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getInt(4), cursor.getInt(5));
                monsters.add(monster);
            } while(cursor.moveToNext());
        }
        if (monsters.size() > 0)
            return monsters.get(0);
        else
            return null;
    }
}
