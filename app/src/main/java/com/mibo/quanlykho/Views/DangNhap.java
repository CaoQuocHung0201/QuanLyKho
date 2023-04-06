package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class DangNhap extends AppCompatActivity {
    TextView btnDangNhap;
    EditText edtTK,edtMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhxa();
    }
    private void anhxa(){
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTK = findViewById(R.id.edtTK);
        edtMK = findViewById(R.id.edtMK);
    }
}