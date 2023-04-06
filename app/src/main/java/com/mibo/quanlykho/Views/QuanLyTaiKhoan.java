package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class QuanLyTaiKhoan extends AppCompatActivity {
    TextView btnThemTK;
    ListView listTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        anhxa();
    }
    private void anhxa(){
        btnThemTK = findViewById(R.id.btnThemTK);
        listTK = findViewById(R.id.listTK);
    }
}