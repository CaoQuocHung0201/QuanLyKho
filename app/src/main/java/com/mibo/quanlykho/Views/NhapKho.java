package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class NhapKho extends AppCompatActivity {
    TextView idNhanvien, barCode, thoiGian, btnChupanh, btnNhap;
    EditText Ten,Gia,soLuong,HSD,thuongHieu,xuatXu;
    Spinner danhmuc;
    RecyclerView listAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho);
        anhxa();


    }
    private void anhxa(){
        idNhanvien = findViewById(R.id.idNhanvien_nhap);
        barCode = findViewById(R.id.barCode_nhap);
        thoiGian = findViewById(R.id.thoiGian_nhap);
        btnChupanh = findViewById(R.id.btnChupanh_nhap);
        btnNhap = findViewById(R.id.btnNhap);
        Ten = findViewById(R.id.edtTenSp_nhap);
        Gia = findViewById(R.id.edtGia_nhap);
        soLuong = findViewById(R.id.edtSoluong_nhap);
        HSD = findViewById(R.id.edtHSD_nhap);
        thuongHieu = findViewById(R.id.edtThuonghieu_nhap);
        xuatXu = findViewById(R.id.edtXuatxu_nhap);
        danhmuc = findViewById(R.id.danhMuc_nhap);
        listAnh = findViewById(R.id.listAnh);
    }
}