package com.example.welkon.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.welkon.models.Army;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private final Context myContext;
    String DBPATH;

    private SQLiteDatabase myDatabase;

    public DBHelper(final Context context) {

        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        DBPATH = context.getApplicationInfo().dataDir + "/databases/";
        this.myContext = context;
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
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("Test", "sdcard mounted and writable");
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("Test", "sdcard mounted readonly");
        }
        else {
            Log.d("Test", "sdcard state: " + state);
        }
        String myPath = DBPATH + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
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


    public boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DBPATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }


    public void checkAndCopyDatabase(){
        boolean dbExist = checkDatabase();
        if (dbExist){
            Log.d("TAG","database already exist");
        }else {
            this.getReadableDatabase();
        }
        try{
            copyDataBase();
        }catch (IOException e){
            e.printStackTrace();
            Log.d("TAG","error db");
        }
    }


    public void copyDataBase() throws IOException{
        InputStream myInput = new FileInputStream(Environment.getExternalStorageDirectory()
                + File.separator + FILE_DIR
                + File.separator + DATABASE_NAME);
        String outFileName = DBPATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

}
