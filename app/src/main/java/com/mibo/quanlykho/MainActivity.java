package com.mibo.quanlykho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mibo.quanlykho.Views.NhapKho;
import com.mibo.quanlykho.Views.QuetMa;
import com.mibo.quanlykho.Views.XuatKho;

public class MainActivity extends AppCompatActivity {

    TextView btnNhap,btnXuat,btnQLTK,btnQLKho;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();

        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuetMa.class));
            }
        });
        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuetMa.class));
            }
        });
        btnQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnQLKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void anhxa(){
        Intent intent = getIntent();
        id=intent.getStringExtra("username");
        btnNhap = findViewById(R.id.btnChonNhap);
        btnXuat = findViewById(R.id.btnChonXuat);
        btnQLTK = findViewById(R.id.btnQLTK);
        btnQLKho = findViewById(R.id.btnQLKho);
    }
}