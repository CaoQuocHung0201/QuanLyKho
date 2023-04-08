package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mibo.quanlykho.Controllers.ImageAdapter;
import com.mibo.quanlykho.R;

import java.util.ArrayList;
import java.util.List;

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

        List<String> imageList = new ArrayList<>();
        imageList.add("https://images.pexels.com/photos/1738675/pexels-photo-1738675.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        imageList.add("https://antimatter.vn/wp-content/uploads/2022/10/hinh-anh-3d.jpg");
        imageList.add("https://haycafe.vn/wp-content/uploads/2021/11/hinh-anh-hoat-hinh-de-thuong-cute-dep-nhat.jpg");
        imageList.add("https://haycafe.vn/wp-content/uploads/2021/11/hinh-anh-hoat-hinh-de-thuong-cute-dep-nhat.jpg");
        imageList.add("https://haycafe.vn/wp-content/uploads/2021/11/hinh-anh-hoat-hinh-de-thuong-cute-dep-nhat.jpg");
        imageList.add("https://haycafe.vn/wp-content/uploads/2021/11/hinh-anh-hoat-hinh-de-thuong-cute-dep-nhat.jpg");

        ImageAdapter adapter = new ImageAdapter(this, imageList);
        listAnh.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listAnh.setAdapter(adapter);


        btnChupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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