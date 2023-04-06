package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class XuatKho extends AppCompatActivity {

    TextView idNhanvien, barCode,Ten, thoiGian,soLuongton, btnXuat;
    EditText Gia,soLuongxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_kho);
        anhxa();
    }
    private void anhxa(){
        idNhanvien = findViewById(R.id.idNhanvien_xuat);
        barCode = findViewById(R.id.barCode_xuat);
        Ten = findViewById(R.id.Ten_xuat);
        thoiGian = findViewById(R.id.thoiGian_xuat);
        soLuongton = findViewById(R.id.soLuongton_xuat);
        btnXuat = findViewById(R.id.btnXuat);
        Gia = findViewById(R.id.edtGia_xuat);
        soLuongxuat = findViewById(R.id.edtSoluong_xuat);
    }
}