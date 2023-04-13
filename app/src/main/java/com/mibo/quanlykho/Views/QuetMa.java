package com.mibo.quanlykho.Views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mibo.quanlykho.Controllers.CaptureAct;
import com.mibo.quanlykho.MainActivity;
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
            if(MainActivity.check.equals("nhap")) {
                Intent intent = new Intent(QuetMa.this, NhapKho.class);
                intent.putExtra("barcode", result.getContents().toString().trim());
                startActivity(intent);
                finish();
                //Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
            }
            if(MainActivity.check.equals("xuat")) {
                Intent intent = new Intent(QuetMa.this, XuatKho.class);
                intent.putExtra("barcodexuat", result.getContents().toString().trim());
                startActivity(intent);
                finish();
                //Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
    });
}