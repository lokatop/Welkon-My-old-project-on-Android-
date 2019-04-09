package com.example.welkon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public static String KEY_FOR_TEXT_FROM_BUTTON = "buttonKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
