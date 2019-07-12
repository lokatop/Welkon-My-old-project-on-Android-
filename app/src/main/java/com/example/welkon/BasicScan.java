package com.example.welkon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
        switch (rawResult.getText()){

            case "1":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"1");
                startActivity(intent);
                break;
            case "2":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"2");
                startActivity(intent);
                break;
            case "3":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"3");
                startActivity(intent);
                break;
            case "4":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"4");
                startActivity(intent);
                break;
            case "5":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"5");
                startActivity(intent);
                break;
            case "6":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"6");
                startActivity(intent);
                break;
            case "7":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"7");
                startActivity(intent);
                break;
            case "8":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"8");
                startActivity(intent);
                break;
            case "9":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"9");
                startActivity(intent);
                break;
            case "10":
                intent.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"10");
                startActivity(intent);
                break;
            case "11":
                intent2.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"11");
                startActivity(intent2);
                break;
            case "12":
                intent2.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"12");
                startActivity(intent2);
                break;
            case "13":
                intent2.putExtra(KEY_FOR_NUMBER_OF_QUIZ,"13");
                startActivity(intent2);
                break;

        }
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
