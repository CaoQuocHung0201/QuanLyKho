package com.mibo.quanlykho.Views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mibo.quanlykho.Controllers.CaptureAct;
import com.mibo.quanlykho.R;

public class QuetMa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quet_ma);

        ScanOptions options=new ScanOptions();
        options.setPrompt("MIBO");

        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLaucher=registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents()!=null){
//            Intent intent=new Intent(MainActivity.this, ImportActivity.class);
//            intent.putExtra("id",result.getContents());
//            startActivity(intent);
//            finish();
        }
    });
}