package com.example.welkon.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.welkon.models.Army;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db2.sqlite";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "mytable";
    public static final String FILE_DIR ="AudioArmy";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PERSON_TITLE = "title";
    public static final String COLUMN_PERSON_SUBTITLE = "subtitle";
    public static final String COLUMN_PERSON_IMAGE = "photoid";
    public static final String COLUMN_PERSON_ALL_IMAGES = "allImages";
    public static final String COLUMN_PERSON_DESCRIPTION = "description";
    public static final String COLUMN_PERSON_GROUPNAME = "groupName";


    private SQLiteDatabase myDatabase;

    public DBHelper(final Context context) {
        super(context, Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void openDatabase(){
        String myPath = Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }


    public boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = Environment.getExternalStorageDirectory()
                    + File.separator + FILE_DIR
                    + File.separator + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){ }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public synchronized void close(){
        if(myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }


    /**Query records, give options to filter results**/
    public List<Army> mainList(String groupName) {
        String query = "";
        if (groupName != null){
            query = "SELECT * FROM " + TABLE_NAME + " WHERE groupName = " + "'"+groupName+"'";
        }else {
            query = "SELECT * FROM " + TABLE_NAME;
        }

        List<Army> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Army mainList;

        if (cursor.moveToFirst()) {
            do {
                mainList = new Army();

                mainList.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                mainList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TITLE)));
                mainList.setSubtitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SUBTITLE)));
                mainList.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
                mainList.setGroupName(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_GROUPNAME)));
                personLinkedList.add(mainList);
            } while (cursor.moveToNext());
        }
        return personLinkedList;
    }

    public Army exMainList(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id="+ id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Army exampleMainList = new Army();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            exampleMainList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_TITLE)));
            exampleMainList.setSubtitle(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_SUBTITLE)));
            exampleMainList.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_IMAGE)));
            exampleMainList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_DESCRIPTION)));
            exampleMainList.setAllImage(cursor.getString(cursor.getColumnIndex(COLUMN_PERSON_ALL_IMAGES)));
        }
        return exampleMainList;
    }

}
