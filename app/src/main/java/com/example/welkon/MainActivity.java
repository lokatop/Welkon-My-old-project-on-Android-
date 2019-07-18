package com.example.welkon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.welkon.Utils.DBHelper;
import com.example.welkon.Utils.MainDBHelper;
import com.example.welkon.Utils.MainDBHelper2;
import com.example.welkon.models.Army;

import static com.example.welkon.BasicScan.KEY_FOR_NUMBER_OF_QUIZ;


public class MainActivity extends AppCompatActivity {

    public static String KEY_FOR_TEXT_FROM_BUTTON = "buttonKey";
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private Class<?> mClss;
    //private MainDBHelper dbHelper;
    private MainDBHelper2 dbHelper2;

    public Army mainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {requestPermissionWrite();}
            }
        }


        dbHelper2 = new MainDBHelper2(this);

        try {
            dbHelper2.checkAndCopyDatabase();
            dbHelper2.openDatabase();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        dbHelper2.close();

    }

    public void newPage(Class clazz,String s){
        Intent intent = new Intent(this, clazz);
        intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
        startActivity(intent);
    }

    public void ListOfModels(View view){
        Button button = (Button)view;
        String buttonText = button.getText().toString();
        newPage(Particular.class,buttonText);
    }

    public void StrihActivity(View view){
        launchActivity(BasicScan.class);
    }



    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "read External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private void requestPermissionWrite() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;

        }
    }


}
