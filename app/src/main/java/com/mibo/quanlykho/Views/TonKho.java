package com.mibo.quanlykho.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mibo.quanlykho.R;

public class TonKho extends AppCompatActivity {

    TextView btnQuaylai;
    ListView listTonkho;
    Spinner danhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ton_kho);
        anhxa();

        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa(){
        btnQuaylai = findViewById(R.id.btnQuaylai_tonkho);
        listTonkho =findViewById(R.id.listTonkho);
        danhMuc = findViewById(R.id.danhmuctonkho);
    }
}