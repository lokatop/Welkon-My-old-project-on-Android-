package com.example.welkon.Utils;

        import android.content.Context;
        import android.content.ContextWrapper;
        import android.database.DatabaseErrorHandler;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Environment;

        import java.io.File;
        import java.io.IOException;

/**
 * Created by jiao on 2016/7/24.
 * From : http://blog.csdn.net/howlaa/article/details/46707159
 * For  : support to write sqlite files in sdcard with class SQLiteOpenHelper
 */
public class SDCardDBContext extends ContextWrapper {
    public SDCardDBContext(Context base){
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        String state = android.os.Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return null;
        }

        String strSDCardPath = android.os.Environment.getExternalStorageDirectory().toString();
        String strSDCardPublicPath = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOCUMENTS).toString();

        String strDBDir = strSDCardPath + "/Goon";
        String strDBPath = strSDCardPath + "/Goon/" + name;
        File dir = new File(strDBDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File dbFile = new File(strDBPath);
        if (dbFile.exists()) {
            return dbFile;
        }

        boolean bCreateSuccess = false;
        try {
            bCreateSuccess = dbFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (bCreateSuccess ? dbFile : null);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     *              android.database.sqlite.SQLiteDatabase.CursorFactory,
     *              android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}