package com.mibo.quanlykho;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mibo.quanlykho.Models.val;
import com.mibo.quanlykho.Views.NhapKho;
import com.mibo.quanlykho.Views.QuanLyTaiKhoan;
import com.mibo.quanlykho.Views.QuetMa;
import com.mibo.quanlykho.Views.TonKho;
import com.mibo.quanlykho.Views.XuatKho;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView btnNhap,btnXuat,btnQLTK,btnQLKho,btnThoat, tvDate;
    String id="";

    public static String check="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();


        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, QuetMa.class));
//                check = "nhap";
                startActivity(new Intent(MainActivity.this, NhapKho.class));
            }
        });
        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, QuetMa.class));
//                check = "xuat";
                startActivity(new Intent(MainActivity.this, XuatKho.class));
            }
        });
        btnQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuanLyTaiKhoan.class));
            }
        });


        btnQLKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TonKho.class));
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    if (checkPermission()){
//        creat_pdf();
//    }
//                else {
//        requestPermission();
//    }

    private void creat_pdf(){
        int pageHeight = 1120;
        int pagewidth = 792;

        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        Paint title = new Paint();

        //Tiêu đề
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(30);
        title.setColor(ContextCompat.getColor(getApplication(), R.color.black));

        canvas.drawText("A portal for IT professionals.", 209, 120, title);
        canvas.drawText("Geeks for Geeks", 209, 80, title);

        //Nội dung
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(getApplication(), R.color.black));
        title.setTextSize(40);

        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);        myPage.getCanvas().drawText("Test sgfjiky",10,10,title);

        pdfDocument.finishPage(myPage);

        String str=Environment.getExternalStorageDirectory().getPath()+ "/GFG.pdf";

        File file = new File(str);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    //Chấp quyền truy cập android 11 trở lên ngược lại
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==val.My_permission_request){
            if (grantResults.length>0){
                boolean write=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean read=grantResults[0]==PackageManager.PERMISSION_GRANTED;

                if (write&&read){
                    //  thực hiện công việc vào file
                    creat_pdf();
                }
                else {
                    Toast.makeText(this, val.err_PERMISSION, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            //  thực hiện công việc vào file
                            creat_pdf();
                        }else{
                            Toast.makeText(MainActivity.this, val.err_PERMISSION, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else {
            int write=ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
            int read=ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
            return write==PackageManager.PERMISSION_GRANTED && read==PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            try {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Uri uri=Uri.fromParts("packege",this.getPackageName(),null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
            catch (Exception e){
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        }else {
            ActivityCompat.requestPermissions(this,new String[]{
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, val.My_permission_request);
        }
    }
    //----------------------------------------------------------------------------------

    private void anhxa(){
        Intent intent = getIntent();
        id=intent.getStringExtra("username");
        btnNhap = findViewById(R.id.btnChonNhap);
        btnXuat = findViewById(R.id.btnChonXuat);
        btnQLTK = findViewById(R.id.btnQLTK);
        btnQLKho = findViewById(R.id.btnQLKho);
        btnThoat = findViewById(R.id.btnThoat_trangchu);
        tvDate = findViewById(R.id.tvDate);

        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                String currentDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault()).format(new Date());
                tvDate.setText(currentDate);
                //time=String.valueOf(currentDate);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 0);

    }
}