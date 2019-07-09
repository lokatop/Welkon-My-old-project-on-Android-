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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_scan);

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

        switch (rawResult.getText()){
            /*
            case "1":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "2":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "3":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "4":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "5":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "6":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "7":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "8":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "9":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;
            case "10":
                Intent intent = new Intent(this, clazz);
                intent.putExtra(KEY_FOR_TEXT_FROM_BUTTON,s);
                startActivity(intent);
                break;*/

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
