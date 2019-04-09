package com.example.welkon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.welkon.fragments.FragmentContainer;
import com.example.welkon.fragments.FragmentList;

public class Particular extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_list);

        if(fragment == null){
            fragment = new FragmentList();
            fm.beginTransaction()
                    .add(R.id.fragment_list,fragment)
                    .commit();
        }

        FragmentManager fm2 = getSupportFragmentManager();
        Fragment fragment2 = fm2.findFragmentById(R.id.detail_fragment_container);

        if(fragment2 == null){
            fragment2 = new FragmentContainer();
            fm.beginTransaction()
                    .add(R.id.detail_fragment_container,fragment2)
                    .commit();
        }
    }

}
