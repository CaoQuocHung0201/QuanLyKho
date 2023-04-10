package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class LichSuNhap_Xuat extends AppCompatActivity {
    TextView tieude, btnLoc, btnXuatAll, btnQuaylai;
    EditText tuNgay, denNgay;
    ListView listLichsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_nhap_xuat);
        anhxa();
    }
    private void anhxa(){
        tieude = findViewById(R.id.tieuDe);
        btnLoc = findViewById(R.id.btnLocNgay);
        btnXuatAll = findViewById(R.id.btnXuatAll);
        btnQuaylai = findViewById(R.id.btnQuaylai_lichsu);
        tuNgay = findViewById(R.id.tuNgay);
        denNgay= findViewById(R.id.denNgay);
        listLichsu = findViewById(R.id.listLichSu);
    }
}