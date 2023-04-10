package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class ThongTinChiTiet extends AppCompatActivity {

    TextView barCode, tenSP, gia1Sp, HSD, soLuong,xuatXu, btnLSNhap, btnLSXuat,btnQuaylai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_chi_tiet);
        anhxa();
    }
    private void anhxa(){
        barCode = findViewById(R.id.barCode_ton);
        tenSP = findViewById(R.id.tenSP_ton);
        gia1Sp = findViewById(R.id.gia1SP_ton);
        HSD = findViewById(R.id.gia1SP_ton);
        soLuong = findViewById(R.id.soluong_ton);
        xuatXu = findViewById(R.id.xuatxu_ton);
        btnLSNhap= findViewById(R.id.btnLSNhap_ton);
        btnLSXuat= findViewById(R.id.btnLSXuat_ton);
        btnQuaylai= findViewById(R.id.btnQuaylai_ton);
    }
}