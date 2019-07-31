package com.example.welkon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        //Сделано спеиально тупо для того, чтобы
        //был показ загрузочного экрана хотя бы на 1-2секунды
        //в нормальном проекте так не делать(просто обычный splash screen)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
