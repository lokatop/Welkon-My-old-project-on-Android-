package com.example.welkon;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.welkon.scan.BaseScannerActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BasicScan extends BaseScannerActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    public static String KEY_FOR_NUMBER_OF_QUIZ = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_scan);
        getSupportActionBar().hide();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, QuizActivity.class);
        Intent intent2 = new Intent(this, VideoActivity.class);
        Intent intent3 = new Intent(this, AudioActivity.class);

        String result = rawResult.getText();
        int key = Integer.parseInt(result);
        //В первом случае отправляем в тест
        if (key > 0 && key <= 10){
            intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,result);
            startActivity(intent);
        }
        //ВО втором в аудио
        else if(key > 10 && key <= 25){
            intent3.putExtra(KEY_FOR_NUMBER_OF_QUIZ,result);
            startActivity(intent3);
        }
        //В третьем в видео
        else if(key > 25 && key <= 30){
            intent2.putExtra(KEY_FOR_NUMBER_OF_QUIZ,result);
            startActivity(intent2);
        }
        else {Toast.makeText(this,"Данный Qr код не поддерживается",Toast.LENGTH_SHORT).show();}

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(BasicScan.this);
            }
        }, 2000);
    }


}
