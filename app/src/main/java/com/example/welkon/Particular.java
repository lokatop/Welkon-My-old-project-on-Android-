package com.example.welkon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.welkon.fragments.FragmentContainer;
import com.example.welkon.fragments.FragmentList;

public class Particular extends AppCompatActivity {


    //Глобальная переменная , в которую приходит значение Id(uuid)
    public static int UUID_INT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_list);

        if(fragment == null){
            fragment = new FragmentList();
            fm.beginTransaction()
                    .add(R.id.fragment_list,fragment)
                    .commit();
        }
    }

}
